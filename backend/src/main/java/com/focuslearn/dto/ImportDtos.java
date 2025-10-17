package com.focuslearn.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ImportDtos {
    public static class ImportRequest {
        public String sourceUrl;
        public UUID destinationJourneyId;
    }

    public static class ImportStatusResponse {
        public UUID jobId;
        public String status;
        public OffsetDateTime createdAt;
        public OffsetDateTime completedAt;
        public String errorMsg;
    }
}
