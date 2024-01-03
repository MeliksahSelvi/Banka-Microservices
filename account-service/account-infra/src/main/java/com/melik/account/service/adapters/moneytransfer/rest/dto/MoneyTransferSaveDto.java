package com.melik.account.service.adapters.moneytransfer.rest.dto;

import com.melik.account.service.moneytransfer.usecase.MoneyTransferCase;
import com.melik.account.service.moneytransfer.valueobject.TransferType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferSaveDto {

    @NotNull(message = "From Customer Id Can Not Be Null")
    @Positive
    private Long accountIdFrom;

    @NotNull(message = "To Customer Id Can Not Be Null")
    @Positive
    private Long accountIdTo;

    @NotNull(message = "Amouunt Can Not Be Null")
    @Positive
    private BigDecimal amount;

    @NotNull(message = "Transfer Type Can Not Be Null")
    private TransferType transferType;

    private String description;

    public MoneyTransferCase toModel() {
        return MoneyTransferCase.builder()
                .accountIdFrom(accountIdFrom)
                .accountIdTo(accountIdTo)
                .amount(amount)
                .description(description)
                .transferType(transferType)
                .build();
    }

}
