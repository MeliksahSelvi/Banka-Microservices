package com.melik.creditcardservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class CreditCardPaymentDto {

    @NotNull(message = "Credit Card Id Can Not Be Null")
    private Long creditCardId;

    @NotNull(message = "Amount Can Not Be Null")
    private BigDecimal amount;
}