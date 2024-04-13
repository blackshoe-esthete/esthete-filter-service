package com.blackshoe.esthete.controller;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/filters")
public class FilterController {

    private final SearchService searchService;

    @GetMapping("/searching")
    public ResponseEntity<Page<FilterDto.SearchFilterResponse>> searchFilter(
            @RequestParam(name = "user_id") UUID userId,
             @RequestParam(required = false) String keyword,
             @RequestParam(required = false) UUID tagId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer size) {

        log.info("searchFilter userId: {}", userId.toString());
        log.info("searchFilter keyword: {}", keyword);

        if (keyword == null) {
            FilterDto.SearchAllRequest searchAllRequest = FilterDto.SearchAllRequest.builder()
                    .userId(userId)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(
                    searchService.searchAllByFilterNameOrWriterNameNotContaining(searchAllRequest, page, size));
        }
        if(tagId == null) {
            FilterDto.SearchWithKeywordRequest searchWithKeywordRequest = FilterDto.SearchWithKeywordRequest.builder()
                    .userId(userId)
                    .keyword(keyword)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(
                    searchService.searchAllByFilterNameOrWriterNameContaining(searchWithKeywordRequest, page, size));
        }

        FilterDto.SearchWithKeywordAndTagRequest searchWithTagRequest = FilterDto.SearchWithKeywordAndTagRequest.builder()
                .userId(userId)
                .keyword(keyword)
                .tagId(tagId)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(
                searchService.searchAllByFilterNameOrWriterNameContainingAndHasTag(searchWithTagRequest, page, size));
    }
}