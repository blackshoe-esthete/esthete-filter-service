package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.exception.KafkaException;
import com.blackshoe.esthete.dto.KafkaConsumerDto;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.KafkaErrorResult;
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
public class KafkaUserInfoConsumer{
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @KafkaListener(topics = "user-create")
    @Transactional
    public void createUser(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaConsumerDto.UserCreate userCreate = null;

        try {
            // 역직렬화
            userCreate = objectMapper.readValue(payload, KafkaConsumerDto.UserCreate.class);
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userCreate);

        UUID userId = UUID.fromString(userCreate.getUserId());

        User user = User.builder()
                .nickname(userCreate.getNickname())
                .build();

        user.setUserId(userId);

        userRepository.save(user);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "user-set-profile")
    @Transactional
    public void setProfileImgUrl(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaConsumerDto.UserProfileImgUrl userProfileImgUrlDto = null;

        try {
            // 역직렬화
            userProfileImgUrlDto = objectMapper.readValue(payload, KafkaConsumerDto.UserProfileImgUrl.class);
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userProfileImgUrlDto);

        UUID userId = UUID.fromString(userProfileImgUrlDto.getUserId());

        final User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new KafkaException(KafkaErrorResult.USER_NOT_FOUND));

        user.updateProfileImgUrl(userProfileImgUrlDto.getProfileImgUrl());

        userRepository.save(user);
        acknowledgment.acknowledge();
    }
}
