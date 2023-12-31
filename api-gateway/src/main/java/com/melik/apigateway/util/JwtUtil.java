package com.melik.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Component
public class JwtUtil {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(APP_KEY).parseClaimsJws(token).getBody();
    }
}
