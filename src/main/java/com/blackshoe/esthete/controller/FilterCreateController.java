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

    @PostMapping("/attribute")
    public ResponseEntity<FilterCreateDto.filterAttributeResponse> saveFilterAttribute(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody FilterCreateDto.filterAttributeRequest requestDto){
        UUID userId = jwtService.extractUserId(accessToken);
        FilterCreateDto.filterAttributeResponse filterAttributeResponse = createService.saveFilterAttribute(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(filterAttributeResponse);
    }


}
