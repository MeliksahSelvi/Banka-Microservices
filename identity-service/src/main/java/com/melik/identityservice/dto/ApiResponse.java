package com.melik.identityservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
public class ApiResponse implements Serializable {

    private HttpStatus status;
    private String message;
    private String description;
    private Map<?, ?> data;
    private Date timeStamp;

    private ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date();
    }

    private ApiResponse(HttpStatus status, String message, String description) {
        this(status, message);
        this.description = description;
    }

    private ApiResponse(HttpStatus status, String message, Map<?, ?> data) {
        this(status, message);
        this.data = data;
    }

    public static ApiResponse of(HttpStatus httpStatus, String message) {
        return new ApiResponse(httpStatus, message);
    }

    public static ApiResponse of(HttpStatus httpStatus, String message, String description) {
        return new ApiResponse(httpStatus, message, description);
    }

    public static ApiResponse of(HttpStatus httpStatus, String message, Map<?, ?> data) {
        return new ApiResponse(httpStatus, message, data);
    }

}
