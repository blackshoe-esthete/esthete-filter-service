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
import java.util.ArrayList;
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
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FilterBasicInfoResponse{
        private String filterId;
        private String filterName;
        private String filterThumbnailUrl;

        @Builder
        public FilterBasicInfoResponse(String filterId, String filterName, String filterThumbnailUrl) {
            this.filterId = filterId;
            this.filterName = filterName;
            this.filterThumbnailUrl = filterThumbnailUrl;
        }
    }
    @Data
    @NoArgsConstructor
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

        @Builder
        public AttributeResponse(String filterId, Float brightness, Float sharpness, Float exposure, Float contrast, Float saturation, Float hue, Float temperature, Float grayScale) {
            this.filterId = filterId;
            this.brightness = brightness != null ? brightness : 0;
            this.sharpness = sharpness != null ? sharpness : 0;
            this.exposure = exposure != null ? exposure : 0;
            this.contrast = contrast != null ? contrast : 0;
            this.saturation = saturation != null ? saturation : 0;
            this.hue = hue != null ? hue : 0;
            this.temperature = temperature != null ? temperature : 0;
            this.grayScale = grayScale != null ? grayScale : 0;
        }
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
        private String filterName;
        private String filterDescription;

        private Long likeCount;
        private String userId;
        private String profileImgUrl;
        private String nickname;
        private Boolean isLike;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TemporaryFilterDetailsResponse {
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

    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReadTemporaryDetailsInfoResponse {
        private UUID temporaryFilterId;
        private String filterName;
        private String description;
        private String filterThumbnail;
        private AttributeResponse filterAttributes;
        private RepresentationImgListResponse representationImgList;
        private FilterTagListResponse filterTagList;
        private LocalDateTime updatedAt;

        @Builder
        public ReadTemporaryDetailsInfoResponse(UUID temporaryFilterId,
                                                String filterName,
                                                String description,
                                                String filterThumbnail,
                                                AttributeResponse filterAttributes,
                                                RepresentationImgListResponse representationImgList,
                                                FilterTagListResponse filterTagList,
                                                LocalDateTime updatedAt) {
            this.temporaryFilterId = temporaryFilterId;
            this.filterName = filterName != null ? filterName : "";
            this.description = description != null ? description : "";
            this.filterThumbnail = filterThumbnail != null ? filterThumbnail : "";
            this.filterAttributes = filterAttributes != null ? filterAttributes : new AttributeResponse(
                    String.valueOf(temporaryFilterId), 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
            this.representationImgList = representationImgList != null ? representationImgList : new RepresentationImgListResponse(new ArrayList<>());
            this.filterTagList = filterTagList != null ? filterTagList : new FilterTagListResponse(new ArrayList<>());
            this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
        }
    }

    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TagResponse {

        private String tagId;
        private String tagName;

        @Builder
        public TagResponse(String tagId, String tagName) {
            this.tagId = tagId;
            this.tagName = tagName;
        }
    }
}
