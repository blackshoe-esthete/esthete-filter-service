package com.blackshoe.esthete.dto;

import com.blackshoe.esthete.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.View;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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


        public SearchFilterResponse(Filter filter, User writer, UUID viewerId, Like like) {
            this.filterId = filter.getFilterId().toString();
            this.filterName = filter.getName();
            this.likeCount = filter.getLikeCount();
            this.filterThumbnailUrl = filter.getThumbnailUrl().getCloudfrontUrl();
            this.isLike = (like != null && like.isUserLike(viewerId));
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
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PurchaseRequest {
        private UUID filterId;
        private UUID userId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PurchaseResponse {
        private String purchasedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CreatedListResponse {
        List<FilterBasicInfoResponse> createdFilterList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PurchasedListResponse {
        List<FilterBasicInfoResponse> purchasedFilterList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FilterBasicInfoResponse{
        private String filterId;
        private String filterName;
        private String filterThumbnailUrl;

    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AttributeResponse {
        private String filterId;
        private Float brightness;
        private Float sharpness;
        private Float exposure;
        private Float contrast;
        private Float saturation;
        private Float hue;
        private Float temperature;
        private Float grayScale;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ThumbnailResponse {
        private String filterThumbnailUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RepresentationImgListResponse {
        private List<String> representationImgList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FilterTagListResponse {
        private List<String> filterTagList;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FilterDetailsResponse {
        private AttributeResponse filterAttributes;
        private String filterThumbnail;
        private RepresentationImgListResponse representationImgList;
        private FilterTagListResponse filterTagList;
        private Long likeCount;
        private String userId;
        private String profileImgUrl;
        private String nickname;
        private Boolean isLike;
        private LocalDateTime createdAt;


    }
}
