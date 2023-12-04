package com.melik.common.module.service.impl;

import com.melik.common.module.dto.LogDto;
import com.melik.common.module.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    @Value("${bank.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, LogDto> kafkaTemplate;

    @Override
    public void log(LogDto logDto) {
        String id = UUID.randomUUID().toString();
        kafkaTemplate.send(topic, id, logDto);
    }
}
