package com.melik.creditcard.service.creditcard.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardRetrieveAll implements UseCase {
    private Optional<Integer> pageOptional;
    private Optional<Integer> sizeOptional;
}
