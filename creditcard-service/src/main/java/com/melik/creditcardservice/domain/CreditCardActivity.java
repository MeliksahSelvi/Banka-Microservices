package com.melik.creditcardservice.domain;

import com.melik.creditcardservice.enums.CardActivityType;
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
@Table(name = "CREDIT_CARD_ACTIVITY")
public class CreditCardActivity {

    @SequenceGenerator(name = "CreditCardActivity", sequenceName = "CREDIT_CARD_ACTIVTY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CreditCardActivity")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CREDIT_CARD", nullable = false)
    private Long creditCardId;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @Column(name = "CARD_ACTIVITY_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private CardActivityType cardActivityType;

}
