package com.melik.account.service.adapters.moneytransfer.jpa.entity;

import com.melik.account.service.common.model.BaseEntity;
import com.melik.account.service.common.valueobject.Money;
import com.melik.account.service.moneytransfer.entity.MoneyTransfer;
import com.melik.account.service.moneytransfer.valueobject.TransferType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Entity
@Getter
@Setter
@Table(name = "money_transfer")
public class MoneyTransferEntity extends BaseEntity {

    @Column(name = "ID_ACCOUNT_FROM")
    private Long accountIdFrom;

    @Column(name = "ID_ACCOUNT_TO")
    private Long accountIdTo;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_DATE")
    private LocalDateTime transferDate;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSFER_TYPE", length = 20)
    private TransferType transferType;

    public MoneyTransfer toModel() {
        return MoneyTransfer.builder()
                .id(super.getId())
                .accountIdFrom(accountIdFrom)
                .accountIdTo(accountIdTo)
                .amount(new Money(amount))
                .description(description)
                .transferType(transferType)
                .transferDate(transferDate)
                .build();
    }
}
