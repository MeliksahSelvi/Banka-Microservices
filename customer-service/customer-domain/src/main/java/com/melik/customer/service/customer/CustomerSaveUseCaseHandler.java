package com.melik.customer.service.customer;

import com.melik.customer.service.common.DomainComponent;
import com.melik.customer.service.common.usecase.UseCaseHandler;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.port.CustomerPort;
import com.melik.customer.service.customer.port.EncryptPasswordPort;
import com.melik.customer.service.customer.usecase.CustomerSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class CustomerSaveUseCaseHandler implements UseCaseHandler<Customer, CustomerSave> {

    private final CustomerPort customerPort;
    private final EncryptPasswordPort encryptPasswordPort;

    @Override
    public Customer handle(CustomerSave customerSave) {
        String password = encryptPasswordPort.encrypt(customerSave.getPassword());
        customerSave.setPassword(password);
        Customer customer = customerPort.persist(customerSave);
        log.info("Customer Saved for firstname and lastname: {} {}",customerSave.getFirstName(),customerSave.getLastName());
        return customer;
    }
}
