package com.melik.accountservice.enums;

public enum CurrencyType {
    TL("TL"),
    EURO("EURO"),
    DOLLAR("DOLLAR");

    private final String type;

    CurrencyType(String type) {
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
