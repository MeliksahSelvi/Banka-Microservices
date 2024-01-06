package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardActivityRetrieve implements UseCase {
    private Long activityId;
}
