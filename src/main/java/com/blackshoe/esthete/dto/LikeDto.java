package com.blackshoe.esthete.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class LikeDto {
    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReadResponse{
        private UUID filterId;
        private String filterName;
        private String filterThumbnailUrl;

        @Builder
        public ReadResponse(UUID filterId, String filterName, String filterThumbnailUrl) {
            this.filterId = filterId;
            this.filterName = filterName;
            this.filterThumbnailUrl = filterThumbnailUrl;
        }
    }
}
