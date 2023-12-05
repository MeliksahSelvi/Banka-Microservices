package com.melik.creditcardservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardPaymentDto {

    @NotNull(message = "Credit Card Id Can Not Be Null")
    private Long creditCardId;

    @NotNull(message = "Amount Can Not Be Null")
    private BigDecimal amount;
}
