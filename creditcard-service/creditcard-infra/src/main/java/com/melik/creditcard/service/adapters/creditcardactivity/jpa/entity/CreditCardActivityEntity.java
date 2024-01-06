package com.melik.creditcard.service.adapters.creditcardactivity.jpa.entity;

import com.melik.creditcard.service.common.model.AbstractEntity;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcardactivity.entity.CreditCardActivity;
import com.melik.creditcard.service.creditcardactivity.valueobject.CardActivityType;
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
@Table(name = "credit_card_activity")
public class CreditCardActivityEntity extends AbstractEntity {

    @Column(name = "ID_CREDIT_CARD", nullable = false)
    private Long creditCardId;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "TRANSACTION_DATE")
    private LocalDateTime transactionDate;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @Column(name = "CARD_ACTIVITY_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private CardActivityType cardActivityType;

    public CreditCardActivity toModel() {
        return CreditCardActivity.builder()
                .id(super.getId())
                .statusType(super.getStatusType())
                .cardActivityType(cardActivityType)
                .amount(new Money(amount))
                .creditCardId(creditCardId)
                .description(description)
                .transactionDate(transactionDate)
                .build();
    }
}
