package com.melik.account.service.moneytransfer.valueobject;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

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
