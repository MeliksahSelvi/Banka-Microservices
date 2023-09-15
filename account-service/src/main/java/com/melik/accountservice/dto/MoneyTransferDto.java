package com.melik.accountservice.dto;

import com.melik.accountservice.enums.TransferType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Getter
@Setter
public class MoneyTransferDto {

    private Long id;
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private Date transferDate;
    private String description;
    private TransferType transferType;
}
