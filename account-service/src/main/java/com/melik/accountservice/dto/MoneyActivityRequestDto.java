package com.melik.accountservice.dto;

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
public class MoneyActivityRequestDto {

    @NotNull(message = "Account Id Can Not Be Null")
    private Long accountId;

    @NotNull(message = "Amount Can Not Be Null")
    private BigDecimal amount;
}
