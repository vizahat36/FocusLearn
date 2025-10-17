package com.focuslearn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @GetMapping("/summary")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getSummary() {
        // TODO: aggregate metrics for current user
        return ResponseEntity.ok().body(new Object());
    }
}
