package com.melik.creditcardservice.domain;

import com.melik.creditcardservice.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Entity
@Getter
@Setter
@Table(name = "CREDIT_CARD")
public class CreditCard  {

    @SequenceGenerator(name = "CreditCard", sequenceName = "CREDIT_CARD_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CreditCard")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CUSTOMER", nullable = false)
    private Long customerId;

    @Column(name = "CARD_NO", nullable = false)
    private Long cardNo;

    @Column(name = "CVV_NO", nullable = false)
    private Long cvvNo;

    @Column(name = "EXPIRE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(name = "TOTAL_LIMIT", precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "AVAILABLE_CARD_LIMIT", precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(name = "CURRENT_DEBT", precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "MINIMUM_PAYMENT_AMOUNT", precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Column(name = "CUTOFF_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date cutoffDate;

    @Column(name = "DUE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 20)
    private StatusType statusType;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
