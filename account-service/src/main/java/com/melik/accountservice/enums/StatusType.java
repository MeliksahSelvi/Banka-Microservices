package com.melik.accountservice.enums;

public enum StatusType {

    ACTIVE("Active"),
    PASSIVE("Passive");

    private final String type;

    StatusType(String type) {
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
