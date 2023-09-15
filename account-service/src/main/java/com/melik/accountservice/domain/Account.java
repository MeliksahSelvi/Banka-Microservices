package com.melik.accountservice.domain;

import com.melik.accountservice.enums.AccountType;
import com.melik.accountservice.enums.CurrencyType;
import com.melik.accountservice.enums.StatusType;
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
@Table(name = "ACCOUNT")
public class Account {

    @SequenceGenerator(name = "Account", sequenceName = "ACCOUNT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "Account")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER")
    private Long customerId;

    @Column(name = "IBAN_NO", length = 26)
    private String ibanNo;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", length = 20)
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 20)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 20)
    private StatusType statusType;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
