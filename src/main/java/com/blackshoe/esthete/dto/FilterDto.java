package com.blackshoe.esthete.dto;

import com.blackshoe.esthete.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class FilterDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchFilterResponse {
        private String filterId;
        private String filterName;
        private Long likeCount;
        private Boolean isLike;
        private String filterThumbnailUrl;

        private String userId;
        private String nickname;
        private String profileImgUrl;

        public SearchFilterResponse(Filter filter, User writer, User viewer, Like like) {
            this.filterId = filter.getFilterId().toString();
            this.filterName = filter.getName();
            this.likeCount = filter.getLikeCount();
            this.filterThumbnailUrl = filter.getThumbnailUrl().getCloudfrontUrl();
            this.isLike = like.isUserLike(viewer);
            this.userId = writer.getUserId().toString();
            this.nickname = writer.getNickname();
            this.profileImgUrl = writer.getProfileImgUrl();
        }

    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchWithKeywordRequest {
        private UUID userId;
        private String keyword;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchWithKeywordAndTagRequest {
        private UUID userId;
        private UUID tagId;
        private String keyword;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchAllRequest {
        private UUID userId;
    }
}
