package com.focuslearn.controller;

import com.focuslearn.dto.ProgressDtos;
import com.focuslearn.model.Progress;
import com.focuslearn.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
@Validated
public class ProgressController {

    @Autowired
    private ProgressRepository progressRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProgressDtos.ProgressResponse> upsertProgress(@RequestBody ProgressDtos.ProgressUpsertRequest req,
                                                                         Principal principal) {
        UUID userId = null;
        try {
            userId = UUID.fromString(principal.getName());
        } catch (Exception ex) {
            // principal may be email; in real app resolve user id from security context
        }

        Optional<Progress> maybe = progressRepository.findByUserIdAndJourneyIdAndStepId(userId, req.journeyId, req.stepId);
        Progress p;
        if (maybe.isPresent()) {
            p = maybe.get();
            // update fields
            if (req.lastPositionSeconds != null) p.setLastPositionSeconds(req.lastPositionSeconds);
            if (req.percentWatched != null) p.setPercentWatched(req.percentWatched);
            if (req.deltaTimeSpentSeconds != null) {
                long existing = p.getTotalTimeSpentSeconds() == null ? 0L : p.getTotalTimeSpentSeconds();
                p.setTotalTimeSpentSeconds(existing + req.deltaTimeSpentSeconds);
            }
            p.setUpdatedAt(OffsetDateTime.now());
        } else {
            p = new Progress();
            p.setUserId(userId);
            p.setJourneyId(req.journeyId);
            p.setStepId(req.stepId);
            p.setLastPositionSeconds(req.lastPositionSeconds);
            p.setPercentWatched(req.percentWatched);
            p.setTotalTimeSpentSeconds(req.deltaTimeSpentSeconds == null ? 0L : req.deltaTimeSpentSeconds);
            p.setUpdatedAt(OffsetDateTime.now());
        }
        Progress saved = progressRepository.save(p);

        ProgressDtos.ProgressResponse res = new ProgressDtos.ProgressResponse();
        res.id = saved.getId();
        res.userId = saved.getUserId();
        res.journeyId = saved.getJourneyId();
        res.stepId = saved.getStepId();
        res.status = saved.getStatus();
        res.lastPositionSeconds = saved.getLastPositionSeconds();
        res.percentWatched = saved.getPercentWatched();
        res.totalTimeSpentSeconds = saved.getTotalTimeSpentSeconds();
        res.updatedAt = saved.getUpdatedAt();

        return ResponseEntity.ok(res);
    }

}
