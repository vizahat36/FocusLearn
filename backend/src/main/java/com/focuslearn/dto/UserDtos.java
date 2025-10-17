package com.focuslearn.dto;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class UserDtos {
    public static class UserProfile {
        public UUID id;
        public String email;
        public String name;
        public String avatarUrl;
        public Set<String> roles;
        public OffsetDateTime createdAt;
    }
}
