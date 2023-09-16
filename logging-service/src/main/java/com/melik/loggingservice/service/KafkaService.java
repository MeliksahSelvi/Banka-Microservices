package com.melik.loggingservice.service;

import com.melik.loggingservice.dto.LogDto;
import com.melik.loggingservice.mapper.LogMapper;
import com.melik.loggingservice.model.LogMessage;
import com.melik.loggingservice.repository.LogMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final LogMessageRepository logMessageRepository;

    @KafkaListener(
            topics = "${bank.kafka.topic}",
            groupId = "${bank.kafka.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload LogDto logDto) {
        log.info("Message received by Logging-Service --> " + logDto.message());
        saveLogToDb(logDto);
    }

    private void saveLogToDb(LogDto logDto) {

        LogMessage logMessage = LogMapper.fromLogDto(logDto);
        logMessageRepository.save(logMessage);
    }
}
