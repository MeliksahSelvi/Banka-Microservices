package com.melik.apigateway.enums;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public enum ErrorMessage {

    MISS_TOKEN("Token Miss In Request Headers");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
