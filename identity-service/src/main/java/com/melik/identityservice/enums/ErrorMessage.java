package com.melik.identityservice.enums;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public enum ErrorMessage {

    TOKEN_IS_NOT_VALID("This Token Is Not Valid"),
    CUSTOMER_NOT_FOUND("Customer Not Found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
