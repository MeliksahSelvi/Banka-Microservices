package com.melik.accountservice.mapper;

import com.melik.accountservice.domain.AccountActivity;
import com.melik.accountservice.dto.AccountActivityDto;
import org.springframework.beans.BeanUtils;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class AccountActivityMapper {

    public static AccountActivityDto fromAccountActivity(AccountActivity accountActivity){
        AccountActivityDto accountActivityDto=new AccountActivityDto();
        BeanUtils.copyProperties(accountActivity,accountActivityDto);
        return accountActivityDto;
    }
}
