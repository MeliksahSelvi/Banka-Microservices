package com.melik.accountservice.enums;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public enum ErrorMessage {

    ACCOUNT_NOT_FOUND("Account Not Found"),
    INSUFFICIENT_BALANCE("Insufficent Balance");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
