package com.melik.customer.service.common.usecase;

import com.melik.customer.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

public interface UseCaseHandler<E, T extends UseCase> {

    E handle(T useCase);
}
