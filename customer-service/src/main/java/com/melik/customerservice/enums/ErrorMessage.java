package com.melik.customerservice.enums;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public enum ErrorMessage {

    CUSTOMER_NOT_FOUND("Customer Not Found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
