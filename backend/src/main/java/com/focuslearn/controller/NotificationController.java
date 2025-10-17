package com.focuslearn.controller;

import com.focuslearn.model.Notification;
import com.focuslearn.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Notification>> listForUser(@RequestParam UUID userId) {
        return ResponseEntity.ok(notificationRepository.findByUserId(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<Notification> create(@RequestBody Notification req) {
        Notification n = new Notification();
        n.setUserId(req.getUserId());
        n.setTitle(req.getTitle());
        n.setMessage(req.getMessage());
        Notification saved = notificationRepository.save(n);
        return ResponseEntity.ok(saved);
    }

    // Simple SSE stream for client notifications. Clients should connect with ?userId=<uuid>
    @GetMapping(path = "/stream", produces = "text/event-stream")
    public SseEmitter stream(@RequestParam UUID userId) {
        SseEmitter emitter = new SseEmitter(0L);
        // naive: send a welcome event then keep open. In production manage emitter registry per-user.
        try {
            emitter.send(SseEmitter.event().name("welcome").data("connected"));
        } catch (Exception e) {
            // ignore
        }
        return emitter;
    }
}
