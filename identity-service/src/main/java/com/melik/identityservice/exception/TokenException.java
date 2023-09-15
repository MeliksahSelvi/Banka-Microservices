package com.melik.identityservice.exception;

import com.melik.identityservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public class TokenException extends RuntimeException{

    public TokenException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
