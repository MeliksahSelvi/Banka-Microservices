package com.melik.customerservice.service.impl;

import com.melik.customerservice.domain.Customer;
import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import com.melik.customerservice.dto.CustomerUpdateDto;
import com.melik.customerservice.enums.ErrorMessage;
import com.melik.customerservice.exception.CustomerException;
import com.melik.customerservice.mapper.CustomerMapper;
import com.melik.customerservice.repository.CustomerRepository;
import com.melik.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.melik.common.module.util.Constants.DEFAULT_PAGE;
import static com.melik.common.module.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        List<Customer> customerList = customerRepository.findAll(pageRequest).toList();
        List<CustomerDto> customerDtoList = convertListToDtoList(customerList);
        return customerDtoList;
    }

    private PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer page = getPageOptionalValue(pageOptional);

        Integer size = getSizeOptionalValue(sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    private Integer getPageOptionalValue(Optional<Integer> pageOptional) {
        return pageOptional.orElse(DEFAULT_PAGE);
    }

    private Integer getSizeOptionalValue(Optional<Integer> sizeOptional) {
        return sizeOptional.orElse(DEFAULT_SIZE);
    }

    private List<CustomerDto> convertListToDtoList(List<Customer> customerList) {
        return customerList.stream().map(customerMapper::fromCustomer).toList();
    }

    @Override
    public CustomerDto findById(Long id) {
        Customer customer = getCustomer(id);
        CustomerDto customerDto = customerMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public CustomerDto save(CustomerSaveDto customerSaveDto) {

        Customer customer = customerMapper.fromSaveDto(customerSaveDto);

        String password = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);

        customer = customerRepository.save(customer);

        CustomerDto customerDto = customerMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public void delete(Long id) {
        Customer customer = getCustomer(id);

        customerRepository.delete(customer);
    }

    private Customer getCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        return customerOptional.orElseThrow(() -> {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_FOUND);
        });
    }

    @Override
    public CustomerDto update(CustomerUpdateDto customerUpdateDto) {
        Customer customer = getCustomer(customerUpdateDto.getId());
        customer = customerRepository.save(customer);
        CustomerDto customerDto = customerMapper.fromCustomer(customer);
        return customerDto;
    }
}
