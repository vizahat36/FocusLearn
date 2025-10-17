package com.focuslearn.repository;

import com.focuslearn.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JourneyRepository extends JpaRepository<Journey, UUID> {
}
