package com.melik.creditcard.service.adapters.creditcardactivity.rest.dto;

import com.melik.creditcard.service.creditcardactivity.usecase.CreditCardActivityRetrieveAll;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardActivityRequestDto {
    @NotNull
    @Positive
    private Long cardId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Optional<Integer> pageOptional;
    private Optional<Integer> sizeOptional;

    public CreditCardActivityRetrieveAll toModel(){
        return CreditCardActivityRetrieveAll.builder()
                .cardId(cardId)
                .startDate(startDate)
                .endDate(endDate)
                .pageOptional(pageOptional)
                .sizeOptional(sizeOptional)
                .build();
    }
}
