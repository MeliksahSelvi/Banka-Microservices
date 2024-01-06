package com.melik.creditcard.service.creditcard.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardRetrieve implements UseCase {
    private Long creditCardId;
}
