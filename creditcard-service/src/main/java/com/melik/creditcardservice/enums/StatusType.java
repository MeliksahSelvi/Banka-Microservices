package com.melik.creditcardservice.enums;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

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
