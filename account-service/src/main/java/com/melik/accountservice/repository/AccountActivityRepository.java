package com.melik.accountservice.repository;

import com.melik.accountservice.domain.AccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

@Repository
public interface AccountActivityRepository extends JpaRepository<AccountActivity,Long> {
}
