package com.melik.customerservice.exception;

import com.melik.customerservice.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class CustomerException extends RuntimeException{

    public CustomerException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
