package com.melik.account.service.accountactivity.valueobject;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

public enum ActivityType {

    WITHDRAW("Withdraw"),//para çekme
    DEPOSIT("Deposit"),//para yatırma
    SEND("Send"),//para gönderme
    GET("Get");// para alma

    private final String type;

    ActivityType(String type) {
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
