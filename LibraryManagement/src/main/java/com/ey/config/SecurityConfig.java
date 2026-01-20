package com.ey.config;




import com.ey.security.JwtAuthenticationFilter;
import com.ey.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

  
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails librarian = User.withUsername("librarian")
                .password(encoder.encode("lib123"))
                .roles("LIBRARIAN")
                .build();

        UserDetails member = User.withUsername("member")
                .password(encoder.encode("mem123"))
                .roles("MEMBER")
                .build();

        return new InMemoryUserDetailsManager(admin, librarian, member);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
           
            .formLogin(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

          
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

         
            .authorizeHttpRequests(auth -> auth
                // public
                .requestMatchers("/api/admin/health", "/api/admin/time").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/refresh").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                // admin only
                .requestMatchers("/api/authors/**").hasRole("ADMIN")
                .requestMatchers("/api/categories/**").hasRole("ADMIN")
                .requestMatchers("/api/members/**").hasRole("ADMIN")
                .requestMatchers("/api/reports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/books/**").hasRole("ADMIN")

                // admin + librarian
                .requestMatchers("/api/loans/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers("/api/reservations/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers("/api/fines/**").hasAnyRole("ADMIN", "LIBRARIAN")

                // member self
                .requestMatchers("/api/loans/member/**").hasRole("MEMBER")
                .requestMatchers("/api/reservations/member/**").hasRole("MEMBER")
                .requestMatchers("/api/fines/member/**").hasRole("MEMBER")

                // create/cancel reservation allowed for all three roles
                .requestMatchers(HttpMethod.POST, "/api/reservations").hasAnyRole("ADMIN","LIBRARIAN","MEMBER")
                .requestMatchers(HttpMethod.POST, "/api/reservations/*/cancel").hasAnyRole("ADMIN","LIBRARIAN","MEMBER")

                // everything else requires authentication
                .anyRequest().authenticated()
            )

            // (5) JWT authentication for API calls (before UsernamePasswordAuthenticationFilter)
            .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



