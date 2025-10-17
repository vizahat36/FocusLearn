package com.focuslearn.controller;

import com.focuslearn.dto.AuthDtos;
import com.focuslearn.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;
    private final com.focuslearn.security.JwtUtils jwtUtils;

    public AuthController(AuthService authService, com.focuslearn.security.JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDtos.AuthResponse> signup(@RequestBody AuthDtos.SignupRequest req) {
        AuthDtos.AuthResponse res = authService.signup(req);
        return ResponseEntity.status(201).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@RequestBody AuthDtos.LoginRequest req) {
        AuthDtos.AuthResponse res = authService.login(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDtos.AuthResponse> refresh(@RequestBody AuthDtos.RefreshRequest req) {
        AuthDtos.AuthResponse res = authService.refresh(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // attempt to get user from token and revoke refresh tokens
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtils.parseClaims(token).getBody().getSubject();
            authService.revokeRefreshTokens(email);
        }
        return ResponseEntity.noContent().build();
    }

}
