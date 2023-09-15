package com.melik.accountservice.exception;

import com.melik.accountservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class MoneyException extends RuntimeException{

    public MoneyException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
