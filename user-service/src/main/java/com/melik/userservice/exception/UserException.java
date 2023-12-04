package com.melik.userservice.exception;

import com.melik.userservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

public class UserException extends RuntimeException{

    public UserException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
