package com.melik.accountservice.dto;

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
public class MoneyActivityRequestDto {

    @NotNull(message = "Account Id Can Not Be Null")
    private Long accountId;

    @NotNull(message = "Amount Can Not Be Null")
    private BigDecimal amount;
}
