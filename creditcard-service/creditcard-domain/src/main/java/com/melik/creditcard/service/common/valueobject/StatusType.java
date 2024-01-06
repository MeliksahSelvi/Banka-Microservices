package com.melik.creditcard.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 03.01.2024
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
