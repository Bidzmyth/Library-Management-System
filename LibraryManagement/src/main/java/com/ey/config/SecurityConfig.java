package com.ey.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration that:
 *  - Uses Spring Security's default /login page (no frontend needed)
 *  - Provides three in-memory users with roles: ADMIN, LIBRARIAN, MEMBER
 *  - Applies RBAC to your existing endpoints exactly as discussed
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // For API convenience (Postman/curl), disable CSRF.
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // ---------- PUBLIC ----------
                .requestMatchers("/api/v1/admin/health", "/api/v1/admin/time").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()

                // ---------- ADMIN ONLY ----------
                .requestMatchers("/api/v1/authors/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/categories/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/members/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/reports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/books").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/books/**").hasRole("ADMIN")

                // ---------- ADMIN + LIBRARIAN ----------
                .requestMatchers("/api/v1/loans/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers("/api/v1/reservations/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .requestMatchers("/api/v1/fines/**").hasAnyRole("ADMIN", "LIBRARIAN")

                // ---------- MEMBER SELF-SERVICE ----------
                .requestMatchers("/api/v1/loans/member/**").hasRole("MEMBER")
                .requestMatchers("/api/v1/reservations/member/**").hasRole("MEMBER")
                .requestMatchers("/api/v1/fines/member/**").hasRole("MEMBER")
                .requestMatchers(HttpMethod.POST, "/api/v1/reservations").hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
                .requestMatchers(HttpMethod.POST, "/api/v1/reservations/*/cancel").hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")

                // ---------- ANYTHING ELSE ----------
                .anyRequest().authenticated()
            )

            // Use Spring Security's default login page at /login (no frontend required)
            .formLogin(Customizer.withDefaults())

            // Default logout with session invalidation
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    /**
     * In-memory users for quick start:
     *  - admin / admin123 : ROLE_ADMIN
     *  - librarian / lib123 : ROLE_LIBRARIAN
     *  - member / mem123 : ROLE_MEMBER
     *
     * You can login with these at /login
     */
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
}




