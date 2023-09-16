package com.melik.identityservice.util;

import com.melik.identityservice.dto.JwtToken;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

public interface CacheUtil {

    JwtToken readTokenMap(String token);

    void writeTokenMap(JwtToken jwtToken);

    void deleteFromTokenMap(String token);
}
