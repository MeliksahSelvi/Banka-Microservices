package com.melik.creditcard.service.creditcardactivity.entity;

import com.melik.creditcard.service.common.model.BaseEntity;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.common.valueobject.StatusType;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import com.melik.creditcard.service.creditcardactivity.valueobject.CardActivityType;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public class CreditCardActivity extends BaseEntity<Long> {

    private final Long creditCardId;
    private final Money amount;
    private final LocalDateTime transactionDate;
    private final CardActivityType cardActivityType;
    private final String description;


    public static Builder builder(){
        return new Builder();
    }

    private CreditCardActivity(Builder builder) {
        this.setId(builder.id);
        setStatusType(builder.statusType);
        creditCardId = builder.creditCardId;
        amount = builder.amount;
        transactionDate = builder.transactionDate;
        cardActivityType = builder.cardActivityType;
        description = builder.description;
    }


    public Long getCreditCardId() {
        return creditCardId;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public CardActivityType getCardActivityType() {
        return cardActivityType;
    }

    public String getDescription() {
        return description;
    }

    public static final class Builder {
        private Long id;
        private Long creditCardId;
        private Money amount;
        private LocalDateTime transactionDate;
        private CardActivityType cardActivityType;
        private String description;
        private StatusType statusType;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder creditCardId(Long val) {
            creditCardId = val;
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

        public Builder cardActivityType(CardActivityType val) {
            cardActivityType = val;
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

        public CreditCardActivity build() {
            return new CreditCardActivity(this);
        }
    }
}
