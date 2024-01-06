package com.melik.user.service.adapters.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.user.service.common.exception.UserDomainBusinessException;
import com.melik.user.service.user.port.JsonPort;
import com.melik.user.service.user.usecase.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class JacksonAdapter implements JsonPort<JwtToken> {

    private final ObjectMapper objectMapper;

    @Override
    public String serialize(JwtToken object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Could not create JwtToken json!", e);
            throw new UserDomainBusinessException("Could not create JwtToken json!", e);
        }
    }

    @Override
    public JwtToken deserialize(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, JwtToken.class);
        } catch (JsonProcessingException e) {
            log.error("Could not read JwtToken from json String!", e);
            throw new UserDomainBusinessException("Could not read JwtToken from json String!", e);
        }
    }
}
