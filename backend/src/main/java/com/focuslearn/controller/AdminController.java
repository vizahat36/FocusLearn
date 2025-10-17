package com.focuslearn.controller;

import com.focuslearn.model.Journey;
import com.focuslearn.model.User;
import com.focuslearn.repository.JourneyRepository;
import com.focuslearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/journeys")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Journey>> listJourneys() {
        return ResponseEntity.ok(journeyRepository.findAll());
    }

    @DeleteMapping("/journeys/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJourney(@PathVariable UUID id) {
        journeyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

