package com.melik.creditcardservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
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
    private Date expireDate;
    private BigDecimal totalLimit;
    private BigDecimal availableCardLimit;
    private BigDecimal currentDebt;
    private BigDecimal minimumPaymentAmount;
    private Date cutoffDate;
    private Date dueDate;
}
