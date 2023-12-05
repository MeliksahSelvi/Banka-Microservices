package com.melik.accountservice.dto;

import com.melik.accountservice.enums.ActivityType;
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
public class AccountActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private Date transactionDate;
    private BigDecimal currentBalance;
    private ActivityType activityType;
}
