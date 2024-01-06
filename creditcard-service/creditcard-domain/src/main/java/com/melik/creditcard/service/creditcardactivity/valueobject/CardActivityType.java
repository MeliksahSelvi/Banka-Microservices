package com.melik.creditcard.service.creditcardactivity.valueobject;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public enum CardActivityType {
    SPEND("Spend"),
    REFUND("Refund"),
    PAYMENT("Payment");

    private final String type;

    CardActivityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
