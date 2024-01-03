package com.melik.account.service.common.usecase;

import com.melik.account.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}
