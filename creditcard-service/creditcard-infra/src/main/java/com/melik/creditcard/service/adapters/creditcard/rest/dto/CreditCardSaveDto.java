package com.melik.creditcard.service.adapters.creditcard.rest.dto;

import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcard.usecase.CreditCardSave;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardSaveDto {

    @NotNull
    @Positive
    private Long customerId;

    @NotNull(message = "Earning Can Not Be Null")
    @Positive
    private BigDecimal earning;

    @NotEmpty
    private String cutoffDay;

    public CreditCardSave toModel(){
        return CreditCardSave.builder()
                .customerId(customerId)
                .cutoffDay(cutoffDay)
                .earning(new Money(earning))
                .build();
    }
}
