package com.blackshoe.esthete.controller;


import com.blackshoe.esthete.dto.KafkaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class KafkaTestController {
    private final KafkaFilterInfoProducerService kafkaFilterInfoProducerService;

    @PostMapping("/kafka")
    public ResponseEntity<?> TestKafka() {
        KafkaDto.FilterInfo filterInfo = KafkaDto.FilterInfo.builder()
                .filterId(UUID.fromString("a720245d-d592-432c-b874-8033cd1b3b2a"))
                .filterName("산뜻한")
                .build();
        kafkaFilterInfoProducerService.createFilter(filterInfo);

        return ResponseEntity.ok("Kafka Test!");
    }
}
