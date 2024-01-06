package com.melik.user.service.user.port;

import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.usecase.JwtToken;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

public interface TokenPort {

    JwtToken generateToken(User user);
}
