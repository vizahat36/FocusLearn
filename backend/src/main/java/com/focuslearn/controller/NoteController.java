package com.focuslearn.controller;

import com.focuslearn.model.Note;
import com.focuslearn.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@Validated
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Note>> listNotes(@RequestParam(required = false) UUID stepId,
                                                @RequestParam(required = false) UUID journeyId,
                                                Principal principal) {
        // for now we return notes for the authenticated user; principal name expected to be UUID string or email
        // assume user id resolution is handled elsewhere and principal.getName() is UUID
        UUID userId = tryParseUUID(principal.getName());
        List<Note> notes;
        if (stepId != null) {
            notes = noteRepository.findByStepId(stepId);
        } else if (journeyId != null) {
            notes = noteRepository.findByJourneyId(journeyId);
        } else {
            notes = noteRepository.findByUserId(userId);
        }
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Note> createNote(@RequestBody Note req, Principal principal) {
        UUID userId = tryParseUUID(principal.getName());
        Note n = new Note();
        n.setUserId(userId);
        n.setJourneyId(req.getJourneyId());
        n.setStep(req.getStep());
        n.setContent(req.getContent());
        n.setTimestampSeconds(req.getTimestampSeconds());
        n.setCreatedAt(OffsetDateTime.now());
        n.setUpdatedAt(OffsetDateTime.now());
        Note saved = noteRepository.save(n);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Note> updateNote(@PathVariable UUID id, @RequestBody Note req, Principal principal) {
        UUID userId = tryParseUUID(principal.getName());
        Optional<Note> maybe = noteRepository.findById(id);
        if (maybe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Note existing = maybe.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        existing.setContent(req.getContent());
        existing.setTimestampSeconds(req.getTimestampSeconds());
        existing.setUpdatedAt(OffsetDateTime.now());
        Note saved = noteRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id, Principal principal) {
        UUID userId = tryParseUUID(principal.getName());
        Optional<Note> maybe = noteRepository.findById(id);
        if (maybe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Note existing = maybe.get();
        if (!existing.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        noteRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }

    private UUID tryParseUUID(String v) {
        try {
            return UUID.fromString(v);
        } catch (Exception ex) {
            // fallback: in some setups principal is email; return null UUID (not ideal)
            return null;
        }
    }
}
