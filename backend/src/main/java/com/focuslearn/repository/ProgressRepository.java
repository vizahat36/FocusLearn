package com.focuslearn.repository;

import com.focuslearn.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProgressRepository extends JpaRepository<Progress, UUID> {
    Optional<Progress> findByUserIdAndJourneyIdAndStepId(UUID userId, UUID journeyId, UUID stepId);
}
