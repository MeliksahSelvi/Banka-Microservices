package com.melik.creditcard.service.adapters.creditcardactivity.rest.dto;

import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.valueobject.CardActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardActivityResponseDto {

    private Long creditCardId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String description;
    private CardActivityType cardActivityType;

    public static CreditCardActivityResponseDto fromModel(CreditCardActivity creditCardActivity) {
        return CreditCardActivityResponseDto.builder()
                .creditCardId(creditCardActivity.getCreditCardId())
                .amount(creditCardActivity.getAmount().getAmount())
                .transactionDate(creditCardActivity.getTransactionDate())
                .description(creditCardActivity.getDescription())
                .cardActivityType(creditCardActivity.getCardActivityType())
                .build();
    }
}
