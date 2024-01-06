package com.melik.customer.service.common.exception;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public class CustomerDomainBusinessException extends RuntimeException{

    public CustomerDomainBusinessException(String message) {
        super(message);
    }

    public CustomerDomainBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
