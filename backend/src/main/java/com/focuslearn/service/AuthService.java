package com.focuslearn.service;

import com.focuslearn.dto.AuthDtos;
import com.focuslearn.model.User;
import com.focuslearn.repository.UserRepository;
import com.focuslearn.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthDtos.AuthResponse signup(AuthDtos.SignupRequest req) {
        User user = new User();
        user.setEmail(req.email);
        user.setPasswordHash(passwordEncoder.encode(req.password));
        user.setName(req.name);
        user.setRoles(new HashSet<>());
        user.getRoles().add("ROLE_USER");
        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getEmail());
        String refresh = UUID.randomUUID().toString();
        RefreshToken rt = new RefreshToken();
        rt.setToken(refresh);
        rt.setUserEmail(user.getEmail());
        rt.setExpiresAt(OffsetDateTime.now().plus(30, ChronoUnit.DAYS));
        refreshTokenRepository.save(rt);

        AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
        res.token = token;
        res.refreshToken = refresh;
        return res;
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest req) {
        User user = userRepository.findByEmail(req.email).orElseThrow();
        if (!passwordEncoder.matches(req.password, user.getPasswordHash())) throw new RuntimeException("Invalid credentials");
        String token = jwtUtils.generateToken(user.getEmail());
        String refresh = UUID.randomUUID().toString();
        RefreshToken rt = new RefreshToken();
        rt.setToken(refresh);
        rt.setUserEmail(user.getEmail());
        rt.setExpiresAt(OffsetDateTime.now().plus(30, ChronoUnit.DAYS));
        refreshTokenRepository.save(rt);

        AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
        res.token = token;
        res.refreshToken = refresh;
        return res;
    }

    public AuthDtos.AuthResponse refresh(AuthDtos.RefreshRequest req) {
        RefreshToken rt = refreshTokenRepository.findByToken(req.refreshToken).orElseThrow();
        if (rt.getExpiresAt().isBefore(OffsetDateTime.now())) {
            refreshTokenRepository.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }
        String token = jwtUtils.generateToken(rt.getUserEmail());
        AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
        res.token = token;
        res.refreshToken = rt.getToken();
        return res;
    }

    public void revokeRefreshTokens(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }
}
