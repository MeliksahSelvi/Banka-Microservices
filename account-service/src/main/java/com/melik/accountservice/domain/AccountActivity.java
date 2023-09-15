package com.melik.accountservice.domain;

import com.melik.accountservice.enums.ActivityType;
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
@Table(name = "ACCOUNT_ACTIVITY")
public class AccountActivity {

    @SequenceGenerator(name = "AccountActivity", sequenceName = "ACCOUNT_ACTIVITY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AccountActivity")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_ACCOUNT")
    private Long accountId;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITY_TYPE", length = 20)
    private ActivityType activityType;
}
