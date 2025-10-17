package com.focuslearn.dto;

public class AuthDtos {
    public static class SignupRequest {
        public String email;
        public String password;
        public String name;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public String tokenType = "Bearer";
        public String refreshToken;
    }

    public static class RefreshRequest {
        public String refreshToken;
    }
}
