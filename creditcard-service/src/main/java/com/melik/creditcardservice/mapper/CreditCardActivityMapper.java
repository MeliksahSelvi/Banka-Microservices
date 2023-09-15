package com.melik.creditcardservice.mapper;

import com.melik.creditcardservice.domain.CreditCardActivity;
import com.melik.creditcardservice.dto.CreditCardActivityDto;
import org.springframework.beans.BeanUtils;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class CreditCardActivityMapper {

    public static CreditCardActivityDto fromCreditCardActivity(CreditCardActivity creditCardActivity) {
        CreditCardActivityDto creditCardActivityDto = new CreditCardActivityDto();
        BeanUtils.copyProperties(creditCardActivity, creditCardActivityDto);
        return creditCardActivityDto;
    }
}
