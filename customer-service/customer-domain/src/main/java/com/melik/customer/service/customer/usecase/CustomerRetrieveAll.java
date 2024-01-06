package com.melik.customer.service.customer.usecase;

import com.melik.customer.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Data
@Builder
public class CustomerRetrieveAll implements UseCase {
    private Optional<Integer> pageOptional;
    private Optional<Integer> sizeOptional;
}
