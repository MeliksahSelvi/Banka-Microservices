package com.melik.identityservice.exception;

import com.melik.identityservice.dto.ApiResponse;
import com.melik.identityservice.dto.LogDto;
import com.melik.identityservice.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(TokenException.class)
    public final ApiResponse handleTokenException(TokenException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        String description = webRequest.getDescription(false);
        log.error(message);

        sendLogToBroker(message, description);
        return ApiResponse.of(HttpStatus.BAD_REQUEST, message, description);
    }

    @ExceptionHandler(CustomerException.class)
    public final ApiResponse handleCustomerException(CustomerException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        String description = webRequest.getDescription(false);
        log.error(message);

        sendLogToBroker(message, description);
        return ApiResponse.of(HttpStatus.NOT_FOUND, message, description);
    }

    private void sendLogToBroker(String message, String description) {
        LogDto logDto = new LogDto(message, description, new Date());
        logService.log(logDto);
    }
}
