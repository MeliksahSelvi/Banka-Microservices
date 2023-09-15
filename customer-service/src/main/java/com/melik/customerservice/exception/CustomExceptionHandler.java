package com.melik.customerservice.exception;

import com.melik.customerservice.dto.ApiResponse;
import com.melik.customerservice.dto.LogDto;
import com.melik.customerservice.service.LogService;
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
 * @Created 08.09.2023
 */

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

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
