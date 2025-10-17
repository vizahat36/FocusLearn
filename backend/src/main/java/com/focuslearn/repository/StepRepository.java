package com.focuslearn.repository;

import com.focuslearn.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StepRepository extends JpaRepository<Step, UUID> {
    List<Step> findByJourneyIdOrderByPositionIndexAsc(UUID journeyId);
}
