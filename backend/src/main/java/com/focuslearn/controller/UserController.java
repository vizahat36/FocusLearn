package com.focuslearn.controller;

import com.focuslearn.dto.UserDtos;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<UserDtos.UserProfile> getUser(@PathVariable UUID id) {
        // TODO: load user profile, apply privacy rules
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id.toString() == principal.username or hasRole('ADMIN')")
    public ResponseEntity<UserDtos.UserProfile> updateUser(@PathVariable UUID id, @RequestBody UserDtos.UserProfile body) {
        // TODO: update profile
        return ResponseEntity.ok(body);
    }
}
