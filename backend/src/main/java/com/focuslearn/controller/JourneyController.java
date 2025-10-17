package com.focuslearn.controller;

import com.focuslearn.dto.JourneyDtos;
import com.focuslearn.model.Journey;
import com.focuslearn.service.JourneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @PostMapping
    public ResponseEntity<Journey> create(@RequestBody JourneyDtos.JourneyCreateRequest req, @AuthenticationPrincipal(expression = "username") String email) {
        // In this simplified example the email is used to find ownerId later; for now ownerId is not resolved
        Journey j = journeyService.create(req, UUID.randomUUID());
        return ResponseEntity.status(201).body(j);
    }

    @GetMapping
    public ResponseEntity<List<Journey>> list() {
        return ResponseEntity.ok(journeyService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journey> get(@PathVariable UUID id) {
        return journeyService.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journey> update(@PathVariable UUID id, @RequestBody JourneyDtos.JourneyUpdateRequest req) {
        Journey j = journeyService.update(id, req);
        return ResponseEntity.ok(j);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        journeyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/fork")
    public ResponseEntity<Journey> fork(@PathVariable UUID id, @AuthenticationPrincipal(expression = "username") String email) {
        Journey fork = journeyService.fork(id, UUID.randomUUID());
        return ResponseEntity.status(201).body(fork);
    }

    @PostMapping("/import")
    public ResponseEntity<Journey> importPlaylist(@RequestParam String url, @RequestParam(required = false) String title, @AuthenticationPrincipal(expression = "username") String email) throws Exception {
        Journey j = journeyService.importFromPlaylist(UUID.randomUUID(), url, title);
        return ResponseEntity.status(201).body(j);
    }

    @PostMapping("/{id}/steps")
    public ResponseEntity<Void> addStep(@PathVariable UUID id, @RequestBody JourneyDtos.StepSummary stepSummary) {
        journeyService.addStep(id, stepSummary);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable UUID id, @PathVariable UUID stepId) {
        journeyService.deleteStep(id, stepId);
        return ResponseEntity.noContent().build();
    }
}
