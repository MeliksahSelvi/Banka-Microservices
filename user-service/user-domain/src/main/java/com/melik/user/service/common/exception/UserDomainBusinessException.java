package com.melik.user.service.common.exception;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public class UserDomainBusinessException extends RuntimeException{

    public UserDomainBusinessException(String message) {
        super(message);
    }

    public UserDomainBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
