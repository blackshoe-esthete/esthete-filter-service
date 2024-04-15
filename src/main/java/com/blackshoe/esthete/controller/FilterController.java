package com.blackshoe.esthete.controller;

import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.service.JwtService;
import com.blackshoe.esthete.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/filters")
public class FilterController {

    private final SearchService searchService;
    private final JwtService jwtService;

    @GetMapping("/searching")
    public ResponseEntity<Page<FilterDto.SearchFilterResponse>> searchFilter(
             @RequestHeader("Authorization") String accessToken,
             @RequestParam(required = false) String keyword,
             @RequestParam(required = false) UUID tagId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer size) {

        UUID userId = jwtService.extractUserId(accessToken);

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

    //제작 필터 리스트 조회

    //구매 필터 리스트 조회

    //필터 설정값 조회

    //필터 썸네일, 대표사진 조회

    //필터 상세보기

    //필터 구매하기
    @PostMapping
}