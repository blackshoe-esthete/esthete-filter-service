package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.dto.KafkaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaUserInfoConsumerServiceImpl implements KafkaUserInfoConsumerService{
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "user-create")
    @Transactional
    public void createUser(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaDto.UserInfo userInfoDto = null;

        try {
            // 역직렬화
            userInfoDto = objectMapper.readValue(payload, KafkaDto.UserInfo.class);
        } catch (Exception e) {
               log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userInfoDto);
        acknowledgment.acknowledge();

    }
}
