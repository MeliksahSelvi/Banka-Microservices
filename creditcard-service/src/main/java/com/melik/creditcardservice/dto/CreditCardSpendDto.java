package com.melik.creditcardservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
public class CreditCardSpendDto {

    @NotNull(message = "Card No Can Not Be Null")
    private Long cardNo;

    @NotNull(message = "Cvv No Can Not Be Null")
    private Long cvvNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Expire Date Can Not Be Null")
    private Date expireDate;

    @NotNull(message = "Amount Can Not Be Null")
    private BigDecimal amount;

    private String description;
}
