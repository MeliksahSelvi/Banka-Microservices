package com.melik.accountservice.mapper;

import com.melik.accountservice.domain.AccountActivity;
import com.melik.accountservice.dto.AccountActivityDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Component
public class AccountActivityMapper {

    public AccountActivityDto fromAccountActivity(AccountActivity accountActivity) {

        AccountActivityDto accountActivityDto = new AccountActivityDto();
        BeanUtils.copyProperties(accountActivity, accountActivityDto);
        return accountActivityDto;
    }
}
