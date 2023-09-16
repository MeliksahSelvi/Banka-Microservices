package com.melik.loggingservice.mapper;

import com.melik.loggingservice.dto.LogDto;
import com.melik.loggingservice.model.LogMessage;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public class LogMapper {

    public static LogMessage fromLogDto(LogDto logDto) {
        LogMessage logMessage = new LogMessage();
        logMessage.setMessage(logDto.message());
        logMessage.setLogDate(logDto.logDate());
        logMessage.setDescription(logDto.description());

        return logMessage;
    }
}
