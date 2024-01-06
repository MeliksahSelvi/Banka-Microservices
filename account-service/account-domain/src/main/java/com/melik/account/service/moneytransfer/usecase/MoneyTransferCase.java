package com.melik.account.service.moneytransfer.usecase;

import com.melik.account.service.common.model.UseCase;
import com.melik.account.service.common.valueobject.Money;
import com.melik.account.service.moneytransfer.valueobject.TransferType;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class MoneyTransferCase implements UseCase {

    private Long accountIdFrom;
    private Long accountIdTo;
    private Money amount;
    private TransferType transferType;
    private String description;
}
