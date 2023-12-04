package com.melik.userservice.enums;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

public enum ErrorMessage {

    USER_NOT_FOUND("User Not Found"),
    EMAIL_IS_ALREADY_USE("Email Is Already Use");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
