package com.melik.user.service.user.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken implements Serializable {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtToken jwtToken = (JwtToken) o;
        return userId.equals(jwtToken.userId) && token.equals(jwtToken.token) && tokenIssuedTime.equals(jwtToken.tokenIssuedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, token, tokenIssuedTime);
    }
}
