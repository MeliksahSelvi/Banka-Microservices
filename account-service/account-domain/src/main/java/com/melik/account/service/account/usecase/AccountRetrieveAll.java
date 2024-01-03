package com.melik.account.service.account.usecase;

import com.melik.account.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class AccountRetrieveAll implements UseCase {

    private Optional<Integer> pageOptional;
    private Optional<Integer> sizeOptional;
}
