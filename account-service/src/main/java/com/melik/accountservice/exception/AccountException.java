package com.melik.accountservice.exception;

import com.melik.accountservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class AccountException extends RuntimeException{

    public AccountException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
