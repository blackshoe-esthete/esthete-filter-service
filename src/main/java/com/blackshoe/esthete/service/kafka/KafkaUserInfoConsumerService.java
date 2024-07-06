package com.blackshoe.esthete.service.kafka;

import org.springframework.kafka.support.Acknowledgment;

public interface KafkaUserInfoConsumerService {
    void createUser(String payload, Acknowledgment acknowledgment);
    void setProfileImgUrl(String payload, Acknowledgment acknowledgment);
}
