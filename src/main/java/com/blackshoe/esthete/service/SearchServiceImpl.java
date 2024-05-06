package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.entity.Tag;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.FilterErrorResult;
import com.blackshoe.esthete.exception.FilterException;
import com.blackshoe.esthete.exception.UserErrorResult;
import com.blackshoe.esthete.exception.UserException;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.TagRepository;
import com.blackshoe.esthete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final FilterRepository filterRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(FilterDto.SearchWithKeywordRequest searchRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UUID userId = searchRequest.getUserId();

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));
        
        return filterRepository.searchAllByFilterNameOrWriterNameContaining(userId, searchRequest.getKeyword(), pageable);

    }

    @Override
    @Transactional
    public Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UUID userId = searchWithTagRequest.getUserId();

        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        Tag tag = tagRepository.findByTagId(searchWithTagRequest.getTagId())
            .orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

        return filterRepository.searchAllByFilterNameOrWriterNameContainingAndHasTag(userId, tag, searchWithTagRequest.getKeyword(), pageable);
    }

    @Override
    @Transactional
    public Page<FilterDto.SearchFilterResponse> searchAll(FilterDto.SearchAllRequest searchRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        User viewer = userRepository.findByUserId(searchRequest.getUserId())
            .orElseThrow(() -> new UserException(UserErrorResult.NOT_FOUND_USER));

        log.info("searchAll user: {}", viewer.getNickname().toString());

        return filterRepository.searchAll(searchRequest.getUserId(), pageable);
    }

}
