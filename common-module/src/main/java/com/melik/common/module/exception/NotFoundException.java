package com.melik.common.module.exception;

/**
 * @Author mselvi
 * @Created 04.12.2023
 */

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
