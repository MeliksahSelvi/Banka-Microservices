package com.melik.identityservice.service.impl;

import com.melik.identityservice.dto.LogDto;
import com.melik.identityservice.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    @Value("${bank.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, LogDto> kafkaTemplate;

    @Override
    public void log(LogDto logDto) {

        String id = UUID.randomUUID().toString();

        kafkaTemplate.send(topic, id, logDto);
        log.info("Log Message Sent From Identity-Service -> {}" + logDto.message());
    }
}
