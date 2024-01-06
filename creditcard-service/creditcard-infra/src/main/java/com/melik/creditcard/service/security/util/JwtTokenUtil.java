package com.melik.creditcard.service.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 05.01.2024
 */

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    public String findUserDetailAsStrByToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String jwtUserDetailsAsStr = claimsJws
                .getBody()
                .getSubject();
        return jwtUserDetailsAsStr;
    }

    public boolean isTokenExpired(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }
}
