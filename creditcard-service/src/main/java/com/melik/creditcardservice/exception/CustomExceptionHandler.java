package com.melik.creditcardservice.exception;

import com.melik.creditcardservice.dto.ApiResponse;
import com.melik.creditcardservice.dto.LogDto;
import com.melik.creditcardservice.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(CreditCardException.class)
    public final ApiResponse handleCreditCardException(CreditCardException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        String description = webRequest.getDescription(false);
        log.error(message);

        sendLogToBroker(message, description);
        return ApiResponse.of(HttpStatus.BAD_REQUEST, message, description);
    }

    @ExceptionHandler(CreditCardActivityException.class)
    public final ApiResponse handleCreditCardActivityException(CreditCardActivityException exception, WebRequest webRequest) {
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
