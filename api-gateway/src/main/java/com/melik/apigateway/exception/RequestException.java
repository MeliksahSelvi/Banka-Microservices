package com.melik.apigateway.exception;

import com.melik.apigateway.enums.ErrorMessage;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public class RequestException extends RuntimeException{

    public RequestException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
