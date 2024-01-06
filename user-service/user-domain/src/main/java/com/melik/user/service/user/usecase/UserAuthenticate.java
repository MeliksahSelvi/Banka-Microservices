package com.melik.user.service.user.usecase;

import com.melik.user.service.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Data
@Builder
public class UserAuthenticate implements UseCase {
    private String email;
    private String password;
}
