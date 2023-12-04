package com.melik.customerservice.repository;

import com.melik.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 31.08.2023
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
