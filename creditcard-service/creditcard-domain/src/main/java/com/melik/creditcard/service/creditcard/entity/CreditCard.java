package com.melik.creditcard.service.creditcard.entity;

import com.melik.creditcard.service.common.model.BaseEntity;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.common.valueobject.StatusType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public class CreditCard extends BaseEntity<Long> {

    private final Long customerId;
    private final Long cardNo;
    private final Long cvvNo;
    private final LocalDate dueDate;
    private final LocalDate expireDate;
    private LocalDate cutoffDate;
    private LocalDateTime cancelDate;
    private Money minimumPaymentAmount;
    private Money totalLimit;
    private Money availableCardLimit;
    private Money currentDebt;


    public static Builder builder() {
        return new Builder();
    }

    private CreditCard(Builder builder) {
        setId(builder.id);
        setStatusType(builder.statusType);
        customerId = builder.customerId;
        cardNo = builder.cardNo;
        cvvNo = builder.cvvNo;
        dueDate = builder.dueDate;
        expireDate = builder.expireDate;
        cutoffDate = builder.cutoffDate;
        cancelDate = builder.cancelDate;
        minimumPaymentAmount = builder.minimumPaymentAmount;
        totalLimit = builder.totalLimit;
        availableCardLimit = builder.availableCardLimit;
        currentDebt = builder.currentDebt;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public Long getCvvNo() {
        return cvvNo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public LocalDate getCutoffDate() {
        return cutoffDate;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public Money getMinimumPaymentAmount() {
        return minimumPaymentAmount;
    }

    public Money getTotalLimit() {
        return totalLimit;
    }

    public Money getAvailableCardLimit() {
        return availableCardLimit;
    }

    public Money getCurrentDebt() {
        return currentDebt;
    }

    public void setCutoffDate(LocalDate cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public void setMinimumPaymentAmount(Money minimumPaymentAmount) {
        this.minimumPaymentAmount = minimumPaymentAmount;
    }

    public void setTotalLimit(Money totalLimit) {
        this.totalLimit = totalLimit;
    }

    public void setAvailableCardLimit(Money availableCardLimit) {
        this.availableCardLimit = availableCardLimit;
    }

    public void setCurrentDebt(Money currentDebt) {
        this.currentDebt = currentDebt;
    }

    public static final class Builder {
        private Long id;
        private Long customerId;
        private Long cardNo;
        private Long cvvNo;
        private LocalDate dueDate;
        private LocalDate expireDate;
        private LocalDate cutoffDate;
        private LocalDateTime cancelDate;
        private Money minimumPaymentAmount;
        private Money totalLimit;
        private Money availableCardLimit;
        private Money currentDebt;
        private StatusType statusType;

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

        public Builder cardNo(Long val) {
            cardNo = val;
            return this;
        }

        public Builder cvvNo(Long val) {
            cvvNo = val;
            return this;
        }

        public Builder dueDate(LocalDate val) {
            dueDate = val;
            return this;
        }

        public Builder expireDate(LocalDate val) {
            expireDate = val;
            return this;
        }

        public Builder cutoffDate(LocalDate val) {
            cutoffDate = val;
            return this;
        }

        public Builder cancelDate(LocalDateTime val) {
            cancelDate = val;
            return this;
        }

        public Builder minimumPaymentAmount(Money val) {
            minimumPaymentAmount = val;
            return this;
        }

        public Builder totalLimit(Money val) {
            totalLimit = val;
            return this;
        }

        public Builder availableCardLimit(Money val) {
            availableCardLimit = val;
            return this;
        }

        public Builder currentDebt(Money val) {
            currentDebt = val;
            return this;
        }

        public Builder statusType(StatusType val) {
            statusType = val;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(this);
        }
    }
}
