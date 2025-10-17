package com.focuslearn.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class NoteDtos {
    public static class NoteCreateRequest {
        public UUID journeyId;
        public UUID stepId;
        public String content;
        public Integer timestampSeconds;
    }

    public static class NoteResponse {
        public UUID id;
        public UUID userId;
        public UUID journeyId;
        public UUID stepId;
        public String content;
        public Integer timestampSeconds;
        public OffsetDateTime createdAt;
    }
}
