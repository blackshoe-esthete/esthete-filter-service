package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.UserDto;
import com.blackshoe.esthete.entity.Tag;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.entity.UserTag;
import com.blackshoe.esthete.exception.*;
import com.blackshoe.esthete.repository.TagRepository;
import com.blackshoe.esthete.repository.UserRepository;
import com.blackshoe.esthete.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

    private final UserTagRepository userTagRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    @Transactional
    @Override
    public void addTag(UUID userId, UUID tagId) {
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
        final Tag tag = tagRepository.findByTagId(tagId).orElseThrow(() -> new RecommendException(RecommendErrorResult.NOT_FOUND_TAG_ID));

        if(userTagRepository.existsByUserAndTag(user, tag)){
            throw new RecommendException(RecommendErrorResult.ALREADY_EXIST_TAG_ID);
        }

        UserTag userTag = UserTag.builder()
            .tag(tag)
            .build();

        userTag.updateUser(user);
        userTagRepository.save(userTag);
    }

    @Transactional
    public void deleteTag(UUID userId, UUID tagId) {
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
        final Tag tag = tagRepository.findByTagId(tagId).orElseThrow(() -> new RecommendException(RecommendErrorResult.NOT_FOUND_TAG_ID));

        if (!userTagRepository.existsByUserAndTag(user, tag)) {
            throw new RecommendException(RecommendErrorResult.NOT_FOUND_TAG_ID);
        }

        userTagRepository.deleteByUserAndTag(user, tag);
    }

    @Override
    @Transactional
    public List<FilterDto.TagResponse> editUserTags(UUID userId, UserDto.EditTagsDto editTagsDto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        // 중복 태그 확인
        Set<String> tagNames = new HashSet<>();
        for (String tagName : editTagsDto.getTagList()) {
            if (!tagNames.add(tagName)) {
                // 중복된 태그가 있는 경우
                throw new TagException(TagErrorResult.DUPLICATE_TAG);
            }
        }

        // 기존 태그들 제거
        userTagRepository.deleteByUser(user);

        // 새로운 태그들을 생성하여 UserTag 엔티티로 변환하여 저장
        List<UserTag> newTagList = editTagsDto.getTagList().stream()
                .map(tagName -> {
                    Tag tag = tagRepository.findByName(tagName)
                            .orElseThrow(() -> new TagException(TagErrorResult.NOT_FOUND_TAG));
                    return UserTag.builder()
                            .user(user)
                            .tag(tag)
                            .build();
                })
                .collect(Collectors.toList());

        // 새로 생성한 UserTag 엔티티들을 저장
        userTagRepository.saveAll(newTagList);

        // 응답에 유저의 태그 리스트를 포함하여 반환
        List<FilterDto.TagResponse> tagResponses = newTagList.stream().map(
                userTag -> FilterDto.TagResponse.builder()
                        .tagId(String.valueOf(userTag.getTag().getTagId()))
                        .tagName(userTag.getTag().getName())
                        .build()
        ).toList();

        return tagResponses;
    }

    @Override
    public List<FilterDto.TagResponse> getTagList(UUID userId) {
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        List<UserTag> userTags = userTagRepository.findByUserWithTags(user);

        List<FilterDto.TagResponse> tagResponses = userTags.stream().map(
            userTag -> FilterDto.TagResponse.builder()
                .tagId(String.valueOf(userTag.getTag().getTagId()))
                .tagName(userTag.getTag().getName())
                .build()
        ).toList();

        return tagResponses;
    }

}
