package com.melik.customerservice.service.impl;

import com.melik.customerservice.domain.Customer;
import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import com.melik.customerservice.dto.CustomerUpdateDto;
import com.melik.customerservice.dto.UserDto;
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

import static com.melik.customerservice.util.Constants.DEFAULT_PAGE;
import static com.melik.customerservice.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

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
        return customerList.stream().map(CustomerMapper::fromCustomer).toList();
    }

    @Override
    public CustomerDto save(CustomerSaveDto customerSaveDto) {

        Customer customer = CustomerMapper.fromSaveDto(customerSaveDto);

        String password = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);

        customer = customerRepository.save(customer);

        CustomerDto customerDto = CustomerMapper.fromCustomer(customer);
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
    public CustomerDto findById(Long id) {
        Customer customer = getCustomer(id);

        CustomerDto customerDto = CustomerMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public CustomerDto update(CustomerUpdateDto customerUpdateDto) {

        Customer customer = getCustomer(customerUpdateDto.getId());

        customer = customerRepository.save(customer);

        CustomerDto customerDto = CustomerMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public UserDto findUserById(Long id) {
        Customer customer = getCustomer(id);

        UserDto userDto = buildUserDto(customer);

        return userDto;
    }

    private UserDto buildUserDto(Customer customer) {
        UserDto userDto = UserDto.builder()
                .identityNo(customer.getIdentityNo())
                .password(customer.getPassword())
                .id(customer.getId())
                .build();
        return userDto;
    }

    @Override
    public UserDto findUserByIdentityNo(Long id) {
        Customer customer = getCustomerByIdentityNo(id);

        UserDto userDto = buildUserDto(customer);
        return userDto;
    }

    private Customer getCustomerByIdentityNo(Long id) {
        Optional<Customer> customerOptional = customerRepository.findByIdentityNo(id);

        return customerOptional.orElseThrow(() -> {
            throw new CustomerException(ErrorMessage.CUSTOMER_NOT_FOUND);
        });
    }

    @Override
    public boolean existCustomer(Long identityNo) {
        boolean isExist = customerRepository.existsCustomerByIdentityNo(identityNo);
        return isExist;
    }
}
