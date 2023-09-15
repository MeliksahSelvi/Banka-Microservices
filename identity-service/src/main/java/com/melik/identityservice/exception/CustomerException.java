package com.melik.identityservice.exception;

import com.melik.identityservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public class CustomerException extends RuntimeException{

    public CustomerException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
