package com.melik.loggingservice.mapper;

import com.melik.common.module.dto.LogDto;
import com.melik.loggingservice.model.LogMessage;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Component
public class LogMapper {

    public LogMessage fromLogDto(LogDto logDto) {
        LogMessage logMessage = new LogMessage();
        logMessage.setMessage(logDto.message());
        logMessage.setLogDate(logDto.logDate());
        logMessage.setDescription(logDto.description());

        return logMessage;
    }
}
