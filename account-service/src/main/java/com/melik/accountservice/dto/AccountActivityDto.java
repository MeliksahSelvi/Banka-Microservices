package com.melik.accountservice.dto;

import com.melik.accountservice.enums.ActivityType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class AccountActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private Date transactionDate;
    private BigDecimal currentBalance;
    private ActivityType activityType;
}
