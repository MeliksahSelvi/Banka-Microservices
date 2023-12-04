package com.melik.customerservice.controller;

import com.melik.customerservice.dto.CustomerDto;
import com.melik.customerservice.dto.CustomerSaveDto;
import com.melik.customerservice.dto.CustomerUpdateDto;
import com.melik.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CustomerDto> customerDtoList = customerService.findAll(pageOptional, sizeOptional);
        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CustomerDto customerDto = customerService.findById(id);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CustomerSaveDto customerSaveDto) {
        CustomerDto customerDto = customerService.save(customerSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer Deleted");
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
        CustomerDto customerDto = customerService.update(customerUpdateDto);
        return ResponseEntity.ok(customerDto);
    }
}
