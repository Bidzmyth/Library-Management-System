package com.ey.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.security.JwtService;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public static class LoginRequest {
        @NotBlank public String username;
        @NotBlank public String password;
    }
    public static class TokenResponse {
        public String tokenType = "Bearer";
        public String accessToken;
        public String refreshToken;
        public long   expiresInSeconds;
        public TokenResponse(String at, String rt, long exp) {
            this.accessToken = at; this.refreshToken = rt; this.expiresInSeconds = exp;
        }
    }
    public static class RefreshRequest { @NotBlank public String refreshToken; }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.startsWith("ROLE_") ? a.substring(5) : a)
                .collect(Collectors.toList());

        String access = jwtService.generateAccessToken(req.username, roles);
        String refresh = jwtService.generateRefreshToken(req.username, roles);
        return ResponseEntity.ok(new TokenResponse(access, refresh, 60 * 60)); // 60 minutes
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest req) {
        var jws = jwtService.parse(req.refreshToken);
        if (!"refresh".equals(jws.getBody().get("type"))) {
            return ResponseEntity.badRequest().build();
        }
        String username = jws.getBody().getSubject();
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) jws.getBody().get("roles");
        String access = jwtService.generateAccessToken(username, roles);
        String refresh = jwtService.generateRefreshToken(username, roles);
        return ResponseEntity.ok(new TokenResponse(access, refresh, 60 * 60));
    }
}

