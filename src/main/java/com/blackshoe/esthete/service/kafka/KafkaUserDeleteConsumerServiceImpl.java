package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.dto.KafkaConsumerDto;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.KafkaErrorResult;
import com.blackshoe.esthete.exception.KafkaException;
import com.blackshoe.esthete.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaUserDeleteConsumerServiceImpl implements KafkaUserDeleteConsumerService {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    @KafkaListener(topics = "user-delete")
    @Transactional
    public void deleteUser(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaConsumerDto.UserDelete userDelete = null;

        try {
            // 역직렬화
            userDelete = objectMapper.readValue(payload, KafkaConsumerDto.UserDelete.class);
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userDelete);

        UUID userId = userDelete.getUserId();
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new KafkaException(KafkaErrorResult.USER_NOT_FOUND));

        userRepository.delete(user);

        acknowledgment.acknowledge();
    }
}
