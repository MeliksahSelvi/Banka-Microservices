package com.melik.account.service.account.usecase;

import com.melik.account.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Data
@Builder
public class AccountRetrieve implements UseCase {
    private Long accountId;
}
