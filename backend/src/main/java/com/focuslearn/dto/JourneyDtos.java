package com.focuslearn.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class JourneyDtos {
    public static class JourneyCreateRequest {
        public String title;
        public String description;
        public String visibility; // PUBLIC | PRIVATE
        public List<String> tags;
        public Integer estimatedDurationMinutes;
    }

    public static class JourneyUpdateRequest {
        public String title;
        public String description;
        public String visibility;
        public List<String> tags;
    }

    public static class StepSummary {
        public UUID id;
        public String title;
        public String type;
        public Integer positionIndex;
        public Integer durationSeconds;
    }

    public static class JourneyResponse {
        public UUID id;
        public String title;
        public String description;
        public String visibility;
        public List<String> tags;
        public UUID ownerId;
        public UUID sourceJourneyId;
        public List<StepSummary> steps;
        public OffsetDateTime createdAt;
        public OffsetDateTime updatedAt;
    }
}
