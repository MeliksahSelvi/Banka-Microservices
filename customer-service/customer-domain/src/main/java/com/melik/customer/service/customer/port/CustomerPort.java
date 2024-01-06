package com.melik.customer.service.customer.port;

import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.usecase.CustomerRetrieve;
import com.melik.customer.service.customer.usecase.CustomerRetrieveAll;
import com.melik.customer.service.customer.usecase.CustomerSave;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public interface CustomerPort {

    List<Customer> retrieveAll(CustomerRetrieveAll customerRetrieveAll);

    Optional<Customer> retrieve(CustomerRetrieve customerRetrieve);

    Customer persist(CustomerSave customerSave);

    Customer update(Customer customer);
}
