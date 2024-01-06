package com.melik.user.service.adapters.cache;

import com.melik.user.service.user.port.CachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Service
@RequiredArgsConstructor
public class RedisAdapter implements CachePort {

    @Value("${bank.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void writeValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }
}
