package com.melik.account.service.common.rest;

import com.melik.account.service.common.exception.AccountDomainBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = AccountDomainBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(AccountDomainBusinessException orderDomainException) {
        log.error(orderDomainException.getMessage(), orderDomainException);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(orderDomainException.getMessage())
                .build();
    }

//    @ResponseBody todo
//    @ExceptionHandler(value = AccountNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleException(AccountNotFoundException accountNotFoundException) {
//        log.error(accountNotFoundException.getMessage(), accountNotFoundException);
//        return ErrorResponse.builder()
//                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
//                .message(accountNotFoundException.getMessage())
//                .build();
//    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected error!")
                .build();
    }
}
