package com.melik.customer.service.adapters.customer.jpa.repository;

import com.melik.customer.service.adapters.customer.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
