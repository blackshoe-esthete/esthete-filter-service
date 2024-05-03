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

}
