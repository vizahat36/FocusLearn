package com.focuslearn.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ProgressDtos {
    public static class ProgressUpsertRequest {
        public UUID journeyId;
        public UUID stepId;
        public Integer lastPositionSeconds;
        public Double percentWatched;
        public Long deltaTimeSpentSeconds;
    }

    public static class ProgressResponse {
        public UUID id;
        public UUID userId;
        public UUID journeyId;
        public UUID stepId;
        public String status;
        public Integer lastPositionSeconds;
        public Double percentWatched;
        public Long totalTimeSpentSeconds;
        public OffsetDateTime updatedAt;
    }
}
