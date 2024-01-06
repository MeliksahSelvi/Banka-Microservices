package com.melik.creditcard.service.adapters.creditcard.rest.dto;

import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
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
public class CreditCardDto {
    private Long id;
    private Long customerId;
    private Long cardNo;
    private Long cvvNo;
    private LocalDate expireDate;
    private BigDecimal totalLimit;
    private BigDecimal availableCardLimit;
    private BigDecimal currentDebt;
    private BigDecimal minimumPaymentAmount;
    private LocalDate cutoffDate;
    private LocalDate dueDate;
    private StatusType statusType;

    public static CreditCardDto fromModel(CreditCard creditCard){
        return CreditCardDto.builder()
                .id(creditCard.getId())
                .customerId(creditCard.getCustomerId())
                .cardNo(creditCard.getCardNo())
                .cvvNo(creditCard.getCvvNo())
                .expireDate(creditCard.getExpireDate())
                .totalLimit(creditCard.getTotalLimit().getAmount())
                .availableCardLimit(creditCard.getAvailableCardLimit().getAmount())
                .currentDebt(creditCard.getCurrentDebt().getAmount())
                .minimumPaymentAmount(creditCard.getMinimumPaymentAmount().getAmount())
                .cutoffDate(creditCard.getCutoffDate())
                .dueDate(creditCard.getDueDate())
                .statusType(creditCard.getStatusType())
                .build();
    }
}
