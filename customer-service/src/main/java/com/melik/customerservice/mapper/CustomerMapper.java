package com.melik.customerservice.mapper;

import com.melik.customerservice.domain.Customer;
import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */

@Component
public class CustomerMapper {

    public CustomerDto fromCustomer(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

    public Customer fromSaveDto(CustomerSaveDto customerSaveDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerSaveDto, customer);
        return customer;
    }
}
