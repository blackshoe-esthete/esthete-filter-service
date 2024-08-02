package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface RecommendService {

    void addTag(UUID userId, UUID tagId);

    void deleteTag(UUID userId, UUID tagId);

    List<FilterDto.TagResponse> getTagList(UUID userId);

    List<FilterDto.TagResponse> editUserTags(UUID userId, UserDto.EditTagsDto editTagsDto);
}
