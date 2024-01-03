package com.melik.account.service.accountactivity.entity;

import com.melik.account.service.accountactivity.valueobject.ActivityType;
import com.melik.account.service.common.model.BaseEntity;
import com.melik.account.service.common.valueobject.Money;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

public class AccountActivity extends BaseEntity<Long> {

    private final Long accountId;
    private final Money amount;
    private final LocalDateTime transactionDate;
    private final Money currentBalance;
    private final ActivityType activityType;

    AccountActivity(Builder builder) {
        setId(builder.id);
        accountId = builder.accountId;
        amount = builder.amount;
        transactionDate = builder.transactionDate;
        currentBalance = builder.currentBalance;
        activityType = builder.activityType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getAccountId() {
        return accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Money getCurrentBalance() {
        return currentBalance;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public static final class Builder {
        private Long id;
        private Long accountId;
        private Money amount;
        private LocalDateTime transactionDate;
        private Money currentBalance;
        private ActivityType activityType;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder accountId(Long val) {
            accountId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transactionDate(LocalDateTime val) {
            transactionDate = val;
            return this;
        }

        public Builder currentBalance(Money val) {
            currentBalance = val;
            return this;
        }

        public Builder activityType(ActivityType val) {
            activityType = val;
            return this;
        }

        public AccountActivity build() {
            return new AccountActivity(this);
        }
    }
}
