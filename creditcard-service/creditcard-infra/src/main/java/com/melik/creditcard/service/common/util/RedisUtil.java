package com.melik.creditcard.service.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.creditcard.service.adapters.rest.dto.JwtToken;
import com.melik.creditcard.service.common.exception.CreditCardDomainBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public JwtToken readValue(String key) {
        String jwtTokenAsStr = (String) redisTemplate.opsForValue().get(key);
        try {
            return objectMapper.readValue(jwtTokenAsStr, JwtToken.class);
        } catch (JsonProcessingException e) {
            log.error("Could not create JwtToken json!", e);
            throw new CreditCardDomainBusinessException("Could not create JwtToken json!", e);
        }
    }
}
