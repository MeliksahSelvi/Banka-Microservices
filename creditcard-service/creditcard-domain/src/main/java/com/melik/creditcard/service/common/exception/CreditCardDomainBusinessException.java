package com.melik.creditcard.service.common.exception;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public class CreditCardDomainBusinessException extends RuntimeException{

    public CreditCardDomainBusinessException(String message) {
        super(message);
    }

    public CreditCardDomainBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
