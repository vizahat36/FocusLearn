package com.focuslearn.controller;

import com.focuslearn.dto.StepDtos;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/journeys/{journeyId}/steps")
@Validated
public class StepController {

    @GetMapping
    public ResponseEntity<List<StepDtos.StepResponse>> listSteps(@PathVariable UUID journeyId) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') and @securityService.isJourneyOwner(#journeyId, principal)")
    public ResponseEntity<StepDtos.StepResponse> createStep(@PathVariable UUID journeyId, @RequestBody StepDtos.StepCreateRequest req) {
        return ResponseEntity.ok(new StepDtos.StepResponse());
    }

    @PutMapping("/{stepId}")
    @PreAuthorize("hasRole('USER') and @securityService.isJourneyOwner(#journeyId, principal)")
    public ResponseEntity<StepDtos.StepResponse> updateStep(@PathVariable UUID journeyId, @PathVariable UUID stepId, @RequestBody StepDtos.StepUpdateRequest req) {
        return ResponseEntity.ok(new StepDtos.StepResponse());
    }

    @DeleteMapping("/{stepId}")
    @PreAuthorize("hasRole('USER') and @securityService.isJourneyOwner(#journeyId, principal)")
    public ResponseEntity<Void> deleteStep(@PathVariable UUID journeyId, @PathVariable UUID stepId) {
        return ResponseEntity.noContent().build();
    }
}
