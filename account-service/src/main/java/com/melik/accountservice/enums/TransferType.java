package com.melik.accountservice.enums;

public enum TransferType {
    RENTAL("Rental"),
    DUE("Due"),
    OTHER("Other");

    private final String type;

    TransferType(String type) {
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
