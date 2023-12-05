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
public class CreditCardSaveDto {

    private Long customerId;
    @NotNull(message = "Earning Can Not Be Null")
    private BigDecimal earning;
    private String cutoffDay;
}
