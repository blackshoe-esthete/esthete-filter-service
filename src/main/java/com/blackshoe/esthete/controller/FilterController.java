package com.blackshoe.esthete.controller;

import com.blackshoe.esthete.dto.FilterCreateDto;
import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.dto.ResponseDto;
import com.blackshoe.esthete.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/filters")
public class FilterController {

    private final SearchService searchService;
    private final PurchasingService purchasingService;
    private final FilterService filterService;
    private final JwtService jwtService;
    private final CreateService createService;

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
                    searchService.searchAll(searchAllRequest, page, size));
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
    @GetMapping("/created")
    public ResponseEntity<ResponseDto<FilterDto.CreatedListResponse>> getCreatedFilterList(
            @RequestHeader("Authorization") String accessToken) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.CreatedListResponse createdFilterListResponse = filterService.getCreatedFilterList(userId);

        ResponseDto responseDto = ResponseDto.success(createdFilterListResponse);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    //구매 필터 리스트 조회
    @GetMapping("/purchased")
    public ResponseEntity<ResponseDto<FilterDto.PurchasedListResponse>> getPurchasedFilterList(
            @RequestHeader("Authorization") String accessToken) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.PurchasedListResponse purchasedFilterListResponse = filterService.getPurchasedFilterList(userId);

        ResponseDto responseDto = ResponseDto.success(purchasedFilterListResponse);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    //필터 설정값 조회
    @GetMapping("/{filterId}/attributes")
    public ResponseEntity<FilterDto.AttributeResponse> getFilterAttributes(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable UUID filterId) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.AttributeResponse filterAttributesResponse = filterService.getFilterAttributes(filterId);

        return ResponseEntity.status(HttpStatus.OK).body(filterAttributesResponse);
    }

    //필터 썸네일, 대표사진 조회
    @GetMapping("/{filterId}/thumbnail")
    public ResponseEntity<FilterDto.ThumbnailResponse> getFilterThumbnail(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable UUID filterId) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.ThumbnailResponse filterThumbnailResponse = filterService.getFilterThumbnail(filterId);

        return ResponseEntity.status(HttpStatus.OK).body(filterThumbnailResponse);
    }

    @GetMapping("/{filterId}/representations")
    public ResponseEntity<ResponseDto<FilterDto.RepresentationImgListResponse>> getFilterRepresentations(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable UUID filterId) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.RepresentationImgListResponse representationImgListResponse = filterService.getFilterRepresentations(filterId);

        ResponseDto responseDto = ResponseDto.success(representationImgListResponse);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    //필터 상세보기
    @GetMapping("/{filterId}/details")
    public ResponseEntity<ResponseDto<FilterDto.FilterDetailsResponse>> getFilterDetail(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable UUID filterId) {

        UUID userId = jwtService.extractUserId(accessToken);

        FilterDto.FilterDetailsResponse filterDetailResponse = filterService.getDetails(filterId, userId);

        ResponseDto responseDto = ResponseDto.success(filterDetailResponse);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    //필터 구매하기
    @PostMapping("/purchase")
    public ResponseEntity<FilterDto.PurchaseResponse> purchaseFilter(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody FilterDto.PurchaseRequest purchaseRequest) {

        UUID userId = jwtService.extractUserId(accessToken);
        purchaseRequest.setUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(purchasingService.purchaseFilter(purchaseRequest));
    }


    @PostMapping(value = "/temporary_filter", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FilterCreateDto.TmpFilterResponse> saveTemporaryFilter(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(name = "thumbnail") MultipartFile thumbnail,
            @RequestPart(name = "representation_img") List<MultipartFile> representationImg,
            @RequestPart FilterCreateDto.CreateFilterRequest requestDto){
        UUID userId = jwtService.extractUserId(accessToken);
        FilterCreateDto.TmpFilterResponse tmpFilterResponse = createService.saveTemporaryFilter(userId, thumbnail, representationImg, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tmpFilterResponse);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FilterCreateDto.CreateFilterResponse> saveFilter(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(name = "thumbnail") MultipartFile thumbnail,
            @RequestPart(name = "representation_img") List<MultipartFile> representationImg,
            @RequestPart FilterCreateDto.CreateFilterRequest requestDto){
        UUID userId = jwtService.extractUserId(accessToken);
        log.info("------------------");
        FilterCreateDto.CreateFilterResponse filterResponse = createService.saveFilter(userId, thumbnail, representationImg, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(filterResponse);
    }

    @DeleteMapping("/{filterId}")
    public ResponseEntity<ResponseDto> un(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable UUID filterId) {

        UUID userId = jwtService.extractUserId(accessToken);
        filterService.deleteFilter(userId, filterId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}