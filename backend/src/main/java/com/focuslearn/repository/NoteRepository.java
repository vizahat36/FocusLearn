package com.focuslearn.repository;

import com.focuslearn.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByStepId(UUID stepId);
    List<Note> findByUserId(UUID userId);
}
