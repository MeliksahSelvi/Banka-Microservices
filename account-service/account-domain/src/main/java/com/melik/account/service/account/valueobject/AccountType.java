package com.melik.account.service.account.valueobject;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

public enum AccountType {
    DRAWING("Drawing"),
    DEPOSIT("Deposit");

    private final String type;

    AccountType(String type) {
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
