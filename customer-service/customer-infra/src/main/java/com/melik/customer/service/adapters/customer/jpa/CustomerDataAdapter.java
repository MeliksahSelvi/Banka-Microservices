package com.melik.customer.service.adapters.customer.jpa;

import com.melik.customer.service.adapters.customer.jpa.entity.CustomerEntity;
import com.melik.customer.service.adapters.customer.jpa.repository.CustomerRepository;
import com.melik.customer.service.common.exception.CustomerDomainBusinessException;
import com.melik.customer.service.common.valueobject.StatusType;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.port.CustomerPort;
import com.melik.customer.service.customer.usecase.CustomerRetrieve;
import com.melik.customer.service.customer.usecase.CustomerRetrieveAll;
import com.melik.customer.service.customer.usecase.CustomerSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.melik.customer.service.common.util.Constants.DEFAULT_PAGE;
import static com.melik.customer.service.common.util.Constants.DEFAULT_SIZE;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDataAdapter implements CustomerPort {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> retrieveAll(CustomerRetrieveAll customerRetrieveAll) {
        PageRequest pageRequest = getPageRequest(customerRetrieveAll);
        Page<CustomerEntity> allCustomer = customerRepository.findAll(pageRequest);
        return allCustomer.toList().stream().map(CustomerEntity::toModel).toList();
    }

    @Override
    public Optional<Customer> retrieve(CustomerRetrieve customerRetrieve) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerRetrieve.getCustomerId());
        if (customerEntity.isEmpty()) {
            log.error("Customer Not Found By id: {}",customerRetrieve.getCustomerId());
            throw new CustomerDomainBusinessException("Customer Not Found By id: "+customerRetrieve.getCustomerId());
        }

        return customerEntity.map(CustomerEntity::toModel);
    }

    @Override
    public Customer persist(CustomerSave customerSave) {
        var customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerSave, customerEntity);
        return customerRepository.save(customerEntity).toModel();
    }

    @Override
    public Customer update(Customer customer) {
        var customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customer, customerEntity);
        customerEntity.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customerEntity).toModel();
    }

    private PageRequest getPageRequest(CustomerRetrieveAll customerRetrieveAll) {
        return PageRequest.of(customerRetrieveAll.getPageOptional().orElse(DEFAULT_PAGE), customerRetrieveAll.getSizeOptional().orElse(DEFAULT_SIZE));
    }
}
