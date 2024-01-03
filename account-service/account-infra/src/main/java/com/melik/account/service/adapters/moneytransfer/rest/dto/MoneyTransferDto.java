package com.melik.account.service.adapters.moneytransfer.rest.dto;

import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.valueobject.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferDto {

    private Long id;
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private LocalDateTime transferDate;
    private String description;
    private TransferType transferType;

    public static MoneyTransferDto fromModel(MoneyTransfer moneyTransfer){
        return MoneyTransferDto.builder()
                .id(moneyTransfer.getId())
                .accountIdFrom(moneyTransfer.getAccountIdFrom())
                .accountIdTo(moneyTransfer.getAccountIdTo())
                .amount(moneyTransfer.getAmount().getAmount())
                .transferDate(moneyTransfer.getTransferDate())
                .transferType(moneyTransfer.getTransferType())
                .description(moneyTransfer.getDescription())
                .build();
    }
}
