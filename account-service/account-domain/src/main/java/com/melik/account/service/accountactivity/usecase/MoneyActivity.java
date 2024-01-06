package com.melik.account.service.accountactivity.usecase;

import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.model.UseCase;
import com.melik.account.service.common.valueobject.Money;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class MoneyActivity implements UseCase {

    private Long accountId;
    private Money amount;
    private ActivityType activityType;
}
