package com.melik.creditcard.service.adapters.creditcardactivity.rest.dto;

import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityPayment;
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
public class CreditCardPaymentDto {

    @NotNull(message = "Credit Card Id Can Not Be Null")
    @Positive
    private Long creditCardId;

    @NotNull(message = "Amount Can Not Be Null")
    @Positive
    private BigDecimal amount;

    public CreditCardActivityPayment toModel(){
        return CreditCardActivityPayment.builder()
                .creditCardId(creditCardId)
                .amount(new Money(amount))
                .build();
    }
}
