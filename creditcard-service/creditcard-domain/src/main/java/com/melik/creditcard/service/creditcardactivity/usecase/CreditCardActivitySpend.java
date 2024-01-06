package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import com.melik.creditcard.service.common.valueobject.Money;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardActivitySpend implements UseCase {
    private Long cardNo;
    private Long cvvNo;
    private LocalDate expireDate;
    private Money amount;
    private String description;
}
