package com.melik.creditcardservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
@RequiredArgsConstructor
public class CreditCardDetails {

    private final Long cardNo;
    private final Date expireDate;
    private final BigDecimal currentDebt;
    private final BigDecimal minimumPaymentAmount;
    private final Date cutoffDate;
    private final Date dueDate;
    private String firstName;
    private String lastName;
    private List<CreditCardActivityDto> creditCardActivityDtoList;
}
