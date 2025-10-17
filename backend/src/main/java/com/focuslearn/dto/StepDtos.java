package com.focuslearn.dto;

import java.util.UUID;

public class StepDtos {
    public static class StepCreateRequest {
        public String title;
        public String type; // VIDEO | ARTICLE | LINK
        public String videoId;
        public String videoUrl;
        public Integer positionIndex;
        public Integer durationSeconds;
        public String content;
    }

    public static class StepUpdateRequest {
        public String title;
        public String videoId;
        public String videoUrl;
        public Integer positionIndex;
        public Integer durationSeconds;
        public String content;
    }

    public static class StepResponse {
        public UUID id;
        public String title;
        public String type;
        public String videoId;
        public String videoUrl;
        public Integer positionIndex;
        public Integer durationSeconds;
        public String content;
    }
}
