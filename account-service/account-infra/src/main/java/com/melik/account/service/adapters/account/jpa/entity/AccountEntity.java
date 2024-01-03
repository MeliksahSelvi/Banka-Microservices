package com.melik.account.service.adapters.account.jpa.entity;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.valueobject.AccountType;
import com.melik.account.service.account.valueobject.CurrencyType;
import com.melik.account.service.common.model.BaseEntity;
import com.melik.account.service.common.valueobject.Money;
import com.melik.account.service.common.valueobject.StatusType;
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
@Table(name = "account")
public class AccountEntity extends BaseEntity {

    @Column(name = "ID_CUS_CUSTOMER")
    private Long customerId;

    @Column(name = "IBAN_NO", length = 26)
    private String ibanNo;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", length = 20)
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 20)
    private AccountType accountType;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime cancelDate;

    @Column(nullable = false)
    private StatusType statusType;

    public Account toModel() {
        return Account.builder()
                .id(super.getId())
                .statusType(statusType)
                .customerId(customerId)
                .currentBalance(new Money(currentBalance))
                .ibanNo(ibanNo)
                .accountType(accountType)
                .currencyType(currencyType)
                .cancelDate(cancelDate)
                .build();
    }
}
