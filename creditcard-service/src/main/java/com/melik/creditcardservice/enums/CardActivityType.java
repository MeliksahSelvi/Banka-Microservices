package com.melik.creditcardservice.enums;

/**
 * @Author mselvi
 * @Created 08.09.2023
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
