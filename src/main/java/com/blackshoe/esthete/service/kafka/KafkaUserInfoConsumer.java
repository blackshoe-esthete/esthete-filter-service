package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.entity.Filter;
import com.blackshoe.esthete.entity.TemporaryFilter;
import com.blackshoe.esthete.exception.KafkaException;
import com.blackshoe.esthete.dto.KafkaConsumerDto;
import com.blackshoe.esthete.entity.User;
import com.blackshoe.esthete.exception.KafkaErrorResult;
import com.blackshoe.esthete.repository.FilterRepository;
import com.blackshoe.esthete.repository.FilterTagRepository;
import com.blackshoe.esthete.repository.TemporaryFilterRepository;
import com.blackshoe.esthete.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaUserInfoConsumer{
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    private final FilterTagRepository filterTagRepository;
    private final TemporaryFilterRepository temporaryFilterRepository;

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

    @KafkaListener(topics = "user-set-nickname")
    @Transactional
    public void setNickname(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaConsumerDto.UserNickname userNicknameDto = null;

        try {
            // 역직렬화
            userNicknameDto = objectMapper.readValue(payload, KafkaConsumerDto.UserNickname.class);
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userNicknameDto);

        UUID userId = UUID.fromString(userNicknameDto.getUserId());
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new KafkaException(KafkaErrorResult.USER_NOT_FOUND));

        user.updateNickname(userNicknameDto.getNickname());

        userRepository.save(user);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "user-delete")
    @Transactional
    public void deleteUser(String payload, Acknowledgment acknowledgment) {
        log.info("received payload='{}'", payload);
        KafkaConsumerDto.UserDelete userDeleteDto = null;

        try {
            // 역직렬화
            userDeleteDto = objectMapper.readValue(payload, KafkaConsumerDto.UserDelete.class);
        } catch (Exception e) {
            log.error("Error while converting json string to user object", e);
        }

        log.info("User info : {}", userDeleteDto);

        UUID userId = UUID.fromString(userDeleteDto.getUserId());
        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new KafkaException(KafkaErrorResult.USER_NOT_FOUND));

        filterTagRepository.deleteByUserOfTemporaryFilter(user);
        temporaryFilterRepository.deleteByUser(user);

        filterTagRepository.deleteByUserOfFilter(user);

        List<Filter> filters = user.getFilters();
        for (Filter filter : filters) {
            filter.freeFilter();
        }

        userRepository.delete(user);

        acknowledgment.acknowledge();
    }

//    @KafkaListener(topics = "user-delete")
//    @Transactional
//    public void deleteUser(String payload) {
//        log.info("received payload='{}'", payload);
//        KafkaConsumerDto.UserDelete userDeleteDto = null;
//
//        try {
//            // 역직렬화
//            userDeleteDto = objectMapper.readValue(payload, KafkaConsumerDto.UserDelete.class);
//        } catch (Exception e) {
//            log.error("Error while converting json string to user object", e);
//        }
//
//        log.info("User info : {}", userDeleteDto);
//
//        UUID userId = UUID.fromString(userDeleteDto.getUserId());
//        final User user = userRepository.findByUserId(userId).orElseThrow(() -> new KafkaException(KafkaErrorResult.USER_NOT_FOUND));
//
//        filterTagRepository.deleteByUserOfTemporaryFilter(user);
//        temporaryFilterRepository.deleteByUser(user);
//
//        filterTagRepository.deleteByUserOfFilter(user);
//
//        List<Filter> filters = user.getFilters();
//        for (Filter filter : filters) {
//            filter.freeFilter();
//        }
//
//        userRepository.delete(user);
//    }
}
