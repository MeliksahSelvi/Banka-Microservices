package com.melik.creditcard.service.adapters.creditcard.jpa.entity;

import com.melik.creditcard.service.common.model.AbstractEntity;
import com.melik.creditcard.service.common.valueobject.Money;
import com.melik.creditcard.service.creditcard.entity.CreditCard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Entity
@Getter
@Setter
@Table(name = "credit_card")
public class CreditCardEntity extends AbstractEntity {

    @Column(name = "ID_CUSTOMER", nullable = false)
    private Long customerId;

    @Column(name = "CARD_NO", nullable = false)
    private Long cardNo;

    @Column(name = "CVV_NO", nullable = false)
    private Long cvvNo;

    @Column(name = "EXPIRE_DATE", nullable = false)
    private LocalDate expireDate;

    @Column(name = "TOTAL_LIMIT", precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "AVAILABLE_CARD_LIMIT", precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(name = "CURRENT_DEBT", precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "MINIMUM_PAYMENT_AMOUNT", precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Column(name = "CUTOFF_DATE", nullable = false)
    private LocalDate cutoffDate;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDate dueDate;

    @Column(name = "CANCEL_DATE")
    private LocalDateTime cancelDate;

    public CreditCard toModel() {
        return CreditCard.builder()
                .id(super.getId())
                .statusType(super.getStatusType())
                .customerId(customerId)
                .cardNo(cardNo)
                .cvvNo(cvvNo)
                .expireDate(expireDate)
                .totalLimit(new Money(totalLimit))
                .availableCardLimit(new Money(availableCardLimit))
                .currentDebt(new Money(currentDebt))
                .minimumPaymentAmount(new Money(minimumPaymentAmount))
                .cutoffDate(cutoffDate)
                .dueDate(dueDate)
                .cancelDate(cancelDate)
                .build();
    }
}
