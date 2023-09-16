package com.melik.customerservice.service;

import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import com.melik.customerservice.dto.CustomerUpdateDto;
import com.melik.customerservice.dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 16.09.2023
 */

public interface CustomerService {

    List<CustomerDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional);

    CustomerDto save(CustomerSaveDto customerSaveDto);

    void delete(Long id);

    CustomerDto findById(Long id);

    CustomerDto update(CustomerUpdateDto customerUpdateDto);

    UserDto findUserById(Long id);

    UserDto findUserByIdentityNo(Long id);

    boolean existCustomer(Long identityNo);
}
