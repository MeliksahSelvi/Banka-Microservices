package com.melik.customer.service.adapters.customer.rest;

import com.melik.customer.service.adapters.customer.rest.dto.CustomerDto;
import com.melik.customer.service.adapters.customer.rest.dto.CustomerSaveDto;
import com.melik.customer.service.common.usecase.UseCaseHandler;
import com.melik.customer.service.common.usecase.VoidUseCaseHandler;
import com.melik.customer.service.customer.entity.Customer;
import com.melik.customer.service.customer.usecase.CustomerRetrieve;
import com.melik.customer.service.customer.usecase.CustomerRetrieveAll;
import com.melik.customer.service.customer.usecase.CustomerSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    private final UseCaseHandler<List<Customer>, CustomerRetrieveAll> customerRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerRetrieve> customerRetrieveUseCaseHandler;
    private final UseCaseHandler<Customer, CustomerSave> customerSaveUseCaseHandler;
    private final VoidUseCaseHandler<CustomerRetrieve> customerCancelVoidUseCaseHandler;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var customerList = customerRetrieveAllUseCaseHandler.handle(toUseCase(pageOptional, sizeOptional));
        return ResponseEntity.ok(customerList.stream().map(CustomerDto::fromModel).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id) {
        var customer = customerRetrieveUseCaseHandler.handle(toUseCase(id));
        return ResponseEntity.ok(CustomerDto.fromModel(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody @Valid CustomerSaveDto customerSaveDto) {
        var customer = customerSaveUseCaseHandler.handle(customerSaveDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerDto.fromModel(customer));
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        customerCancelVoidUseCaseHandler.handle(toUseCase(id));
        return ResponseEntity.ok("Customer Cancelled");
    }

    private CustomerRetrieveAll toUseCase(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        return CustomerRetrieveAll.builder().pageOptional(pageOptional).sizeOptional(sizeOptional).build();
    }

    private CustomerRetrieve toUseCase(Long id) {
        return CustomerRetrieve.builder().customerId(id).build();
    }
}
