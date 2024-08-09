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
    @Transactional(readOnly = true)
    public Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(FilterDto.SearchWithKeywordRequest searchRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UUID userId = searchRequest.getUserId();

        return filterRepository.searchAllByFilterNameOrWriterNameContaining(userId, searchRequest.getKeyword(), pageable);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        UUID userId = searchWithTagRequest.getUserId();

        Tag tag = tagRepository.findByTagId(searchWithTagRequest.getTagId())
            .orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

        return filterRepository.searchAllByFilterNameOrWriterNameContainingAndHasTag(userId, tag, searchWithTagRequest.getKeyword(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilterDto.SearchFilterResponse> searchAll(FilterDto.SearchAllRequest searchRequest, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return filterRepository.searchAll(searchRequest.getUserId(), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FilterDto.SearchFilterResponse> search(UUID userId, String keyword, UUID tagId, Integer page, Integer size) {
        log.info("Searching filters - userId: {}, keyword: {}, tagId: {}, page: {}, size: {}", userId, keyword, tagId, page, size);

        if (keyword == null && tagId == null) {
            FilterDto.SearchAllRequest searchAllRequest = FilterDto.SearchAllRequest.builder()
                    .userId(userId)
                    .build();

            return searchAll(searchAllRequest, page, size);
        }

        if (keyword == null) {
            FilterDto.SearchWithTagRequest searchWithTagRequest = FilterDto.SearchWithTagRequest.builder()
                    .userId(userId)
                    .tagId(tagId)
                    .build();

            return searchAllByTag(searchWithTagRequest, page, size);
        }

        if (tagId == null) {
            FilterDto.SearchWithKeywordRequest searchWithKeywordRequest = FilterDto.SearchWithKeywordRequest.builder()
                    .userId(userId)
                    .keyword(keyword)
                    .build();

            return searchAllByFilterNameOrWriterNameContaining(searchWithKeywordRequest, page, size);
        }

        FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest = FilterDto.SearchWithKeywordAndTagRequest.builder()
                .userId(userId)
                .keyword(keyword)
                .tagId(tagId)
                .build();

        return searchAllByFilterNameOrWriterNameContainingAndHasTag(searchWithTagRequest, page, size);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FilterDto.SearchFilterResponse> searchAllByTag(FilterDto.SearchWithTagRequest searchWithTagRequest, Integer page, Integer size) {
        log.info("Searching filters by tag - userId: {}, tagId: {}, page: {}, size: {}", searchWithTagRequest.getUserId(), searchWithTagRequest.getTagId(), page, size);

        Pageable pageable = PageRequest.of(page, size);

        UUID userId = searchWithTagRequest.getUserId();

        Tag tag = tagRepository.findByTagId(searchWithTagRequest.getTagId())
                .orElseThrow(() -> new FilterException(FilterErrorResult.NOT_FOUND_TAG));

        return filterRepository.searchAllByTag(userId, tag, pageable);
    }
}
