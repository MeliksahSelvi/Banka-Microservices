package com.melik.account.service.moneytransfer.entity;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.common.model.BaseEntity;
import com.melik.account.service.common.valueobject.Money;
import com.melik.account.service.common.valueobject.StatusType;
import com.melik.account.service.moneytransfer.valueobject.TransferType;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 02.01.2024
 */

public class MoneyTransfer extends BaseEntity<Long> {

    private final Long accountIdFrom;
    private final Long accountIdTo;
    private final Money amount;
    private final TransferType transferType;
    private final LocalDateTime transferDate;
    private String description;

    public static Builder builder() {
        return new Builder();
    }

    private MoneyTransfer(Builder builder) {
        setId(builder.id);
        setStatusType(builder.statusType);
        accountIdFrom = builder.accountIdFrom;
        accountIdTo = builder.accountIdTo;
        amount = builder.amount;
        transferType = builder.transferType;
        transferDate = builder.transferDate;
        description = builder.description;
    }

    public Long getAccountIdFrom() {
        return accountIdFrom;
    }

    public Long getAccountIdTo() {
        return accountIdTo;
    }

    public Money getAmount() {
        return amount;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class Builder {
        private Long id;
        private Long accountIdFrom;
        private Long accountIdTo;
        private Money amount;
        private TransferType transferType;
        private LocalDateTime transferDate;
        private String description;
        private StatusType statusType;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder accountIdFrom(Long val) {
            accountIdFrom = val;
            return this;
        }

        public Builder accountIdTo(Long val) {
            accountIdTo = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transferType(TransferType val) {
            transferType = val;
            return this;
        }

        public Builder transferDate(LocalDateTime val) {
            transferDate = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder statusType(StatusType val) {
            statusType = val;
            return this;
        }

        public MoneyTransfer build() {
            return new MoneyTransfer(this);
        }
    }
}
