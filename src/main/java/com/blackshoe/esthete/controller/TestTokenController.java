package com.blackshoe.esthete.controller;


import com.blackshoe.esthete.dto.KafkaConsumerDto;
import com.blackshoe.esthete.service.JwtService;
import com.blackshoe.esthete.service.kafka.KafkaUserInfoConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/test/token")
@RequiredArgsConstructor
public class TestTokenController {
    private final JwtService jwtService;
    private final KafkaUserInfoConsumer kafkaUserInfoConsumer;
    private final ObjectMapper objectMapper;
    @GetMapping()
    public ResponseEntity<String> test(
            @RequestHeader("Authorization") String authorizationHeader){
        UUID userId = jwtService.extractUserId(authorizationHeader);
        System.out.println("--------------------" + userId);
        return ResponseEntity.ok("sucess");
    }

//    @DeleteMapping("/kafka")
//    public ResponseEntity<String> testKafka() throws JsonProcessingException {
//        UUID userId = UUID.fromString("5a0db2eb-f4bc-4fa3-ae47-8381ed0da1ab");
//        KafkaConsumerDto.UserDelete userDelete = KafkaConsumerDto.UserDelete.builder()
//                .userId(userId.toString())
//                .build();
//        kafkaUserInfoConsumer.deleteUser(objectMapper.writeValueAsString(userDelete));
//        return ResponseEntity.ok("sucess");
//    }
}
