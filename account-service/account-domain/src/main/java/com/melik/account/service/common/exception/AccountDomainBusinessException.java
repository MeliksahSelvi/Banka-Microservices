package com.melik.account.service.common.exception;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public class AccountDomainBusinessException extends RuntimeException{

    public AccountDomainBusinessException(String message) {
        super(message);
    }
}
