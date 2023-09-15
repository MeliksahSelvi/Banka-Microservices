package com.melik.customerservice.mapper;

import com.melik.customerservice.domain.Customer;
import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import org.springframework.beans.BeanUtils;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */

public class CustomerMapper {

    public static CustomerDto fromCustomer(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }

    public static Customer fromSaveDto(CustomerSaveDto customerSaveDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerSaveDto, customer);
        return customer;
    }
}
