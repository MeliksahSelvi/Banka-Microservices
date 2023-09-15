package com.melik.creditcardservice.exception;

import com.melik.creditcardservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class CreditCardActivityException extends RuntimeException {

    public CreditCardActivityException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
