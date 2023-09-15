package com.melik.accountservice.domain;

import com.melik.accountservice.enums.TransferType;
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
@Table(name = "MONEY_TRANSFER")
public class MoneyTransfer {

    @SequenceGenerator(name = "MoneyTransfer", sequenceName = "MONEY_TRANSFER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "MoneyTransfer")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_ACCOUNT_FROM")
    private Long accountIdFrom;

    @Column(name = "ID_ACCOUNT_TO")
    private Long accountIdTo;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_DATE")
    private Date transferDate;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSFER_TYPE", length = 20)
    private TransferType transferType;
}
