package com.blackshoe.esthete.service.kafka;

import com.blackshoe.esthete.dto.KafkaDto;

public interface KafkaFilterInfoProducerService {
    void createFilter(KafkaDto.FilterInfo filterInfo);
}
