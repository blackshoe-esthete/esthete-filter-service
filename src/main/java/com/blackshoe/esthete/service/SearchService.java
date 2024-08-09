package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface SearchService {
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(FilterDto.SearchWithKeywordRequest searchRequest, int page, int size);
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest, int page, int size);

    Page<FilterDto.SearchFilterResponse> searchAll(FilterDto.SearchAllRequest searchRequest, int page, int size);

    Page<FilterDto.SearchFilterResponse> search(UUID userId, String keyword, UUID tagId, Integer page, Integer size);

    @Transactional(readOnly = true)
    Page<FilterDto.SearchFilterResponse> searchAllByTag(FilterDto.SearchWithTagRequest searchWithTagRequest, Integer page, Integer size);
}
