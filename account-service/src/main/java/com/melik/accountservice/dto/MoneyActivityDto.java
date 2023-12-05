package com.melik.accountservice.dto;

import com.melik.accountservice.enums.ActivityType;
import lombok.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private ActivityType activityType;
}
