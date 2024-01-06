package com.melik.user.service.user.port;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

public interface CachePort {

    void writeValue(String key, Object value);
}
