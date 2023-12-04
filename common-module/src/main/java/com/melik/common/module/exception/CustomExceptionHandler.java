package com.melik.common.module.exception;

import com.melik.common.module.dto.LogDto;
import com.melik.common.module.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 04.12.2023
 */


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        String description = webRequest.getDescription(false);
        log.error(message);

        CompletableFuture.runAsync(() -> sendLogToBroker(message, description));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


    @ExceptionHandler(BankException.class)
    public final ResponseEntity handleBankException(BankException exception, WebRequest webRequest) {
        String message = exception.getMessage();
        String description = webRequest.getDescription(false);
        log.error(message);

        CompletableFuture.runAsync(() -> sendLogToBroker(message, description));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    private void sendLogToBroker(String message, String description) {
        LogDto logDto = new LogDto(message, description, new Date());
        logService.log(logDto);
    }
}
