package com.blackshoe.esthete.service;

import com.blackshoe.esthete.dto.FilterDto;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContaining(FilterDto.SearchWithKeywordRequest searchRequest, int page, int size);
    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameContainingAndHasTag(FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest, int page, int size);

    Page<FilterDto.SearchFilterResponse> searchAllByFilterNameOrWriterNameNotContaining(FilterDto.SearchAllRequest searchRequest, int page, int size);
}
