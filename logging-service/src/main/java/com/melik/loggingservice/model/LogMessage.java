package com.melik.loggingservice.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Getter
@Setter
@Document(value = "LogMessage")
public class LogMessage {

    @Id
    private Long id;
    private String message;
    private String description;
    private Date logDate;
}
