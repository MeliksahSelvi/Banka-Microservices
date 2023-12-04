package com.melik.creditcardservice.mapper;

import com.melik.creditcardservice.domain.CreditCardActivity;
import com.melik.creditcardservice.dto.CreditCardActivityDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Component
public class CreditCardActivityMapper {

    public CreditCardActivityDto fromCreditCardActivity(CreditCardActivity creditCardActivity) {
        CreditCardActivityDto creditCardActivityDto = new CreditCardActivityDto();
        BeanUtils.copyProperties(creditCardActivity, creditCardActivityDto);
        return creditCardActivityDto;
    }
}
