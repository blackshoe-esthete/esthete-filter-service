package com.blackshoe.esthete.controller;


import com.blackshoe.esthete.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/test/token")
@RequiredArgsConstructor
public class TestTokenController {
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<String> test(
            @RequestHeader("Authorization") String authorizationHeader){
        UUID userId = jwtService.extractUserId(authorizationHeader);
        System.out.println("--------------------" + userId);
        return ResponseEntity.ok("sucess");
    }
}
