package com.melik.creditcardservice.enums;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public enum ErrorMessage {

    CREDIT_CARD_NOT_FOUND("Account Not Found"),
    INVALID_CARD("Invalid Credit Card"),
    CREDIT_CARD_EXPIRED("This Credit Card Is Expired"),
    INSUFFICIENT_CREDIT_CARD_LIMIT("This Credit Card Limit Is Insufficient"),
    ACTIVITY_NOT_FOUND("Credit Card Activity Not Found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
