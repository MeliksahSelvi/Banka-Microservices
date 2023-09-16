package com.melik.customerservice.controller;

import com.melik.customerservice.dto.*;
import com.melik.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ApiResponse findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CustomerDto> customerDtoList = customerService.findAll(pageOptional, sizeOptional);
        return ApiResponse.of(HttpStatus.OK, "Customers Retrieved", Map.of("customerDtoList", customerDtoList));
    }

    @GetMapping("/{id}")
    public ApiResponse findById(@PathVariable Long id) {
        CustomerDto customerDto = customerService.findById(id);
        return ApiResponse.of(HttpStatus.OK, "Customer Retrieved", Map.of("customerDto", customerDto));
    }

    @PostMapping
    public ApiResponse save(@RequestBody @Valid CustomerSaveDto customerSaveDto) {
        CustomerDto customerDto = customerService.save(customerSaveDto);

        return ApiResponse.of(HttpStatus.CREATED, "Customer Saved", Map.of("customerDto", customerDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        customerService.delete(id);
        return ApiResponse.of(HttpStatus.OK, "Customer Deleted");
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
        CustomerDto customerDto = customerService.update(customerUpdateDto);
        return ApiResponse.of(HttpStatus.OK, "Customer Updated", Map.of("customerDto", customerDto));
    }

    @GetMapping("/user/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        UserDto userDto = customerService.findUserById(id);
        return userDto;
    }

    @GetMapping("/user/identityNo/{id}")
    public UserDto findUserByIdentityNo(@PathVariable Long id) {
        UserDto userDto = customerService.findUserByIdentityNo(id);
        return userDto;
    }

    @GetMapping("/exist/{id}")
    public boolean existCustomer(@PathVariable Long id) {
        boolean isExist = customerService.existCustomer(id);
        return isExist;
    }
}
