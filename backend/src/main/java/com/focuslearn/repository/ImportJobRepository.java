package com.focuslearn.repository;

import com.focuslearn.model.ImportJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportJobRepository extends JpaRepository<ImportJob, UUID> {
}
