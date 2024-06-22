package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.dto.KafkaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaFilterInfoProducerServiceImpl implements KafkaFilterInfoProducerService{
    private final KafkaProducer kafkaProducer;

    private final ObjectMapper objectMapper;

    @Override
    public void createFilter(KafkaDto.FilterInfo filterInfo) {
        String topic = "filter-create";

        String filterJsonString;
        try{
            //직렬화
            filterJsonString = objectMapper.writeValueAsString(filterInfo);
        } catch (JsonProcessingException e) {
            log.error("Error while converting user object to json string", e);
            throw new RuntimeException("Error while converting user object to json string", e);
        }

        kafkaProducer.send(topic, filterJsonString);
    }
}
