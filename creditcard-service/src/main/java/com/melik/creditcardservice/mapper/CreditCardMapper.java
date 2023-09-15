package com.melik.creditcardservice.mapper;

import com.melik.creditcardservice.domain.CreditCard;
import com.melik.creditcardservice.dto.CreditCardDto;
import org.springframework.beans.BeanUtils;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class CreditCardMapper {

    public static CreditCardDto fromCreditCard(CreditCard creditCard){
        CreditCardDto creditCardDto= new CreditCardDto();
        BeanUtils.copyProperties(creditCard,creditCardDto);
        return creditCardDto;
    }
}
