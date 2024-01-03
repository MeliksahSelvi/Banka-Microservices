package com.melik.account.service.accountactivity.usecase;

import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class MoneyActivity implements UseCase {

    private Long accountId;
    private BigDecimal amount;
    private ActivityType activityType;
}
