package com.melik.user.service.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Component
public class JwtTokenUtil {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    public boolean isTokenExpired(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public String findUserDetailAsStrByToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String jwtUserDetailsAsStr = claimsJws
                .getBody()
                .getSubject();
        return jwtUserDetailsAsStr;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }
}
