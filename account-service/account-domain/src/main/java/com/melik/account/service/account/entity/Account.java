package com.melik.account.service.account.entity;

import com.melik.account.service.account.valueobject.AccountType;
import com.melik.account.service.account.valueobject.CurrencyType;
import com.melik.account.service.common.model.BaseEntity;
import com.melik.account.service.common.valueobject.Money;
import com.melik.account.service.common.valueobject.StatusType;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

public class Account extends BaseEntity<Long> {
    private final Long customerId;
    private final CurrencyType currencyType;
    private final AccountType accountType;
    private Money currentBalance;
    private final String ibanNo;
    private StatusType statusType;
    private LocalDateTime cancelDate;


    private Account(Builder builder) {
        setId(builder.id);
        customerId = builder.customerId;
        ibanNo = builder.ibanNo;
        currentBalance = builder.currentBalance;
        currencyType = builder.currencyType;
        accountType = builder.accountType;
        statusType = builder.statusType;
        cancelDate = builder.cancelDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getIbanNo() {
        return ibanNo;
    }

    public Money getCurrentBalance() {
        return currentBalance;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public void setCurrentBalance(Money currentBalance) {
        this.currentBalance = currentBalance;
    }

    public static final class Builder {
        private Long id;
        private Long customerId;
        private String ibanNo;
        private Money currentBalance;
        private CurrencyType currencyType;
        private AccountType accountType;
        private StatusType statusType;
        private LocalDateTime cancelDate;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder customerId(Long val) {
            customerId = val;
            return this;
        }

        public Builder ibanNo(String val) {
            ibanNo = val;
            return this;
        }

        public Builder currentBalance(Money val) {
            currentBalance = val;
            return this;
        }

        public Builder currencyType(CurrencyType val) {
            currencyType = val;
            return this;
        }

        public Builder accountType(AccountType val) {
            accountType = val;
            return this;
        }

        public Builder statusType(StatusType val) {
            statusType = val;
            return this;
        }

        public Builder cancelDate(LocalDateTime val) {
            cancelDate = val;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
