package com.melik.accountservice.dto;

import com.melik.accountservice.enums.TransferType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferSaveDto {

    @NotNull(message = "From Customer Id Can Not Be Null")
    private Long accountIdFrom;

    @NotNull(message = "To Customer Id Can Not Be Null")
    private Long accountIdTo;

    @NotNull(message = "Amouunt Can Not Be Null")
    private BigDecimal amount;

    @NotNull(message = "Transfer Type Can Not Be Null")
    private TransferType transferType;

    private String description;
}
