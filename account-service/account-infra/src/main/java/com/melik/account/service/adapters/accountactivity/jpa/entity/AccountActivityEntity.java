package com.melik.account.service.adapters.accountactivity.jpa.entity;

import com.melik.account.service.accountactivity.entity.AccountActivity;
import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.model.AbstractEntity;
import com.melik.account.service.common.valueobject.Money;
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
@Table(name = "account_activity")
public class AccountActivityEntity extends AbstractEntity {

    @Column(name = "ID_ACCOUNT")
    private Long accountId;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "TRANSACTION_DATE")
    private LocalDateTime transactionDate;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITY_TYPE", length = 20)
    private ActivityType activityType;

    public AccountActivity toModel() {
        return AccountActivity.builder()
                .id(super.getId())
                .statusType(super.getStatusType())
                .amount(new Money(amount))
                .activityType(activityType)
                .currentBalance(new Money(currentBalance))
                .accountId(accountId)
                .transactionDate(transactionDate)
                .build();
    }
}
