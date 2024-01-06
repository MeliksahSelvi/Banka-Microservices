package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class CreditCardActivityRetrieveAll implements UseCase {
    private Long cardId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Optional<Integer> pageOptional;
    private Optional<Integer> sizeOptional;
}
