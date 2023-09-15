package com.melik.accountservice.enums;

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
