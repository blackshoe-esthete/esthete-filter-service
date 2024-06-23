package com.blackshoe.esthete.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KafkaConsumerDto {
    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CommentReport {
        private String reporterId;
        private String writerId;
        private String reportType;
        private String reportDescription;

        private String commentId;
        private String commentContent;
        @Builder
        public CommentReport(String reporterId, String writerId, String reportType, String reportDescription, String commentId, String commentContent) {
            this.reporterId = reporterId;
            this.writerId = writerId;
            this.reportType = reportType;
            this.reportDescription = reportDescription;
            this.commentId = commentId;
            this.commentContent = commentContent;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PhotoReport {
        private String reporterId;
        private String writerId;
        private String reportType;
        private String reportDescription;
        private String photoId;
        private String photoImgUrl;
        private String exhibitionTitle;
        @Builder
        public PhotoReport(String reporterId, String writerId, String reportType, String reportDescription, String photoId, String photoImgUrl, String exhibitionTitle) {
            this.reporterId = reporterId;
            this.writerId = writerId;
            this.reportType = reportType;
            this.reportDescription = reportDescription;
            this.photoId = photoId;
            this.photoImgUrl = photoImgUrl;
            this.exhibitionTitle = exhibitionTitle;
        }
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UserCreate {
        private String userId;
        private String nickname;
        private String email;

        @Builder
        public UserCreate(String userId, String nickname, String email) {
            this.userId = userId;
            this.nickname = nickname;
            this.email = email;
        }
    }
    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UserProfileImgUrl {
        private String userId;
        private String profileImgUrl;

        @Builder
        public UserProfileImgUrl(String userId, String profileImgUrl) {
            this.userId = userId;
            this.profileImgUrl = profileImgUrl;
        }
    }
}
