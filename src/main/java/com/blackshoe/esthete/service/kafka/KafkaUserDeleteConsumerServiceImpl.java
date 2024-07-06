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

}
