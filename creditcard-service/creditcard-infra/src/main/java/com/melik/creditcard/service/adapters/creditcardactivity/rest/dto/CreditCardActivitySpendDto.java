package com.melik.creditcard.service.adapters.creditcardactivity.rest.dto;

import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivitySpend;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardActivitySpendDto {

    @NotNull(message = "Card No Can Not Be Null")
    @Positive
    private Long cardNo;

    @NotNull(message = "Cvv No Can Not Be Null")
    @Positive
    private Long cvvNo;

    @NotNull(message = "Expire Date Can Not Be Null")
    private LocalDate expireDate;

    @NotNull(message = "Amount Can Not Be Null")
    @Positive
    private BigDecimal amount;

    @NotEmpty(message = "Description can not be empty")
    private String description;

    public CreditCardActivitySpend toModel() {
        return CreditCardActivitySpend.builder()
                .cardNo(cardNo)
                .cvvNo(cvvNo)
                .expireDate(expireDate)
                .amount(new Money(amount))
                .description(description)
                .build();
    }
}
