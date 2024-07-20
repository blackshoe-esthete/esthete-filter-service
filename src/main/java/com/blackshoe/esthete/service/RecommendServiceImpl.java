package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Tag;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.entity.UserTag;
import com.blackshoe.esthete.exception.RecommendErrorResult;
import com.blackshoe.esthete.exception.RecommendException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.TagRepository;
import com.blackshoe.esthete.repository.UserRepository;
import com.blackshoe.esthete.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        // TODO: Implement this method
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
