package com.melik.accountservice.dto;

import com.melik.accountservice.enums.TransferType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferDto {

    private Long id;
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private Date transferDate;
    private String description;
    private TransferType transferType;
}
