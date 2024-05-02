package com.blackshoe.esthete.controller;

import com.blackshoe.esthete.dto.FilterCreateDto;
import com.blackshoe.esthete.dto.FilterDto;
import com.blackshoe.esthete.service.CreateService;
import com.blackshoe.esthete.service.JwtService;
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
public class FilterCreateController {
    private final CreateService createService;
    private final JwtService jwtService;

//    @PostMapping("/attribute")
//    public ResponseEntity<FilterCreateDto.filterAttributeResponse> saveFilterAttribute(
//            @RequestHeader("Authorization") String accessToken,
//            @RequestBody FilterCreateDto.filterAttributeRequest requestDto){
//        UUID userId = jwtService.extractUserId(accessToken);
//        FilterCreateDto.filterAttributeResponse filterAttributeResponse = createService.saveFilterAttribute(userId, requestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(filterAttributeResponse);
//    }
//
//    @PostMapping(value = "/{temporaryFilterId}/thumbnail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<FilterCreateDto.createTmpFilterResponse> saveFilterThumnail(
//            @PathVariable UUID temporaryFilterId,
//            @RequestPart(name = "thumbnail") MultipartFile thumbnail){
//        FilterCreateDto.ThumbnailImgUrl thumbnailImgUrl = createService.uploadFilterThumbnail(thumbnail, temporaryFilterId);
//        FilterCreateDto.createTmpFilterResponse tmpFilterResponse = createService.saveThumbnailImage(thumbnailImgUrl, temporaryFilterId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(tmpFilterResponse);
//    }
//
//    @PostMapping(value = "/{temporary_filter_id}/representation", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<FilterCreateDto.createTmpFilterResponse> saveFilterRepresentation(
//            @PathVariable(name = "temporary_filter_id") UUID temporaryFilterId,
//            @RequestPart(name = "representation_img") List<MultipartFile> representationImg){
//        List<FilterCreateDto.RepresentationImgUrl> representationImgUrls = createService.uploadFilterRepresentativeImages(representationImg, temporaryFilterId);
//        FilterCreateDto.createTmpFilterResponse representationImageResponse = createService.saveRepresentationImage(representationImgUrls, temporaryFilterId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(representationImageResponse);
//    }
//
//    @PostMapping("/{temporary_filter_id}/information")
//    ResponseEntity<FilterCreateDto.createTmpFilterResponse> saveTmpFilterInformation(
//            @PathVariable(name = "temporary_filter_id") UUID temporaryFilterId,
//            @RequestBody FilterCreateDto.TmpFilterInformationRequest requestDto){
//        FilterCreateDto.createTmpFilterResponse tmpFilterResponse = createService.saveTempFilterInformation(temporaryFilterId, requestDto);
//        return  ResponseEntity.status(HttpStatus.OK).body(tmpFilterResponse);
//    }
//
//    @PostMapping("/{temporary_filter_id}/completion")
//    ResponseEntity<FilterCreateDto.createTmpFilterResponse> completeFilterCreation(
//            @PathVariable(name = "temporary_filter_id") UUID temporaryFilterId,
//            @RequestBody FilterCreateDto.TmpFilterInformationRequest requestDto){
//        FilterCreateDto.createTmpFilterResponse filterResponse = createService.saveTempFilterToFilter(temporaryFilterId, requestDto);
//        return  ResponseEntity.status(HttpStatus.OK).body(filterResponse);
//    }

    @PostMapping(value = "/temporary_filter", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FilterCreateDto.TmpFilterResponse> saveTemporaryFilter(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(name = "thumbnail") MultipartFile thumbnail,
            @RequestPart(name = "representation_img") List<MultipartFile> representationImg,
            @RequestBody FilterCreateDto.CreateTmpFilterRequest requestDto){
        UUID userId = jwtService.extractUserId(accessToken);
        FilterCreateDto.TmpFilterResponse tmpFilterResponse = createService.saveTemporaryFilter(userId, thumbnail, representationImg, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tmpFilterResponse);
    }

//    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<FilterCreateDto.TmpFilterResponse> saveFilter(
//            @RequestHeader("Authorization") String accessToken,
//            @RequestPart(name = "thumbnail") MultipartFile thumbnail,
//            @RequestPart(name = "representation_img") List<MultipartFile> representationImg,
//            @RequestBody FilterCreateDto.CreateTmpFilterRequest requestDto){
//        UUID userId = jwtService.extractUserId(accessToken);
//        FilterCreateDto.TmpFilterResponse tmpFilterResponse = createService.saveFilter(userId, thumbnail, representationImg, requestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(tmpFilterResponse);
//    }

}
