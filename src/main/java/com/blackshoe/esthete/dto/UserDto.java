package com.blackshoe.esthete.dto;

import com.blackshoe.esthete.entity.UserTag;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


public class UserDto {
    @Data
    @NoArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EditTagsDto {
        private List<String> tagList;
    }
}
