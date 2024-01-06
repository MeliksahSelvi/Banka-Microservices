package com.melik.customer.service.customer;

import com.melik.customer.service.common.DomainComponent;
import com.melik.customer.service.common.exception.CustomerDomainBusinessException;
import com.melik.customer.service.common.usecase.UseCaseHandler;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.port.CustomerPort;
import com.melik.customer.service.customer.usecase.CustomerRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CustomerRetrieveUseCaseHandler implements UseCaseHandler<Customer, CustomerRetrieve> {

    private final CustomerPort customerPort;

    @Override
    public Customer handle(CustomerRetrieve customerRetrieve) {
        Optional<Customer> optionalCustomer = customerPort.retrieve(customerRetrieve);
        if (optionalCustomer.isEmpty()) {
            log.error("Customer Not Found By id: {}",customerRetrieve.getCustomerId());
            throw new CustomerDomainBusinessException("Customer Not Found By id: "+customerRetrieve.getCustomerId());
        }
        log.info("Customer Retrieve for id : {}", customerRetrieve.getCustomerId());
        return optionalCustomer.get();
    }
}
