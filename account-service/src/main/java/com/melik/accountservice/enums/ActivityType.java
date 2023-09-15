package com.melik.accountservice.enums;

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
