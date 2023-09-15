package com.melik.creditcardservice.dto;

import com.melik.creditcardservice.enums.CardActivityType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class CreditCardActivityDto {

    private Long creditCardId;
    private BigDecimal amount;
    private Date transactionDate;
    private String description;
    private CardActivityType cardActivityType;
}
