package com.blackshoe.esthete.service.kafka;

import org.springframework.kafka.support.Acknowledgment;

public interface KafkaUserDeleteConsumerService {
    void deleteUser(String payload, Acknowledgment acknowledgment);
}
