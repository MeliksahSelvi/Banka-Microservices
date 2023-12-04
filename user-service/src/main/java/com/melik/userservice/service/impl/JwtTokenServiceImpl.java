package com.melik.userservice.service.impl;

import com.melik.userservice.security.JwtToken;
import com.melik.userservice.security.JwtUserDetails;
import com.melik.userservice.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 01.12.2023
 */

@Component
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${bank.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    @Override
    public JwtToken genereteJwtToken(Authentication authentication) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();

        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(jwtUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();

        String fullToken = generateFullToken(token);

        JwtToken jwtToken = createJwtToken(fullToken, jwtUserDetails.getId());

        return jwtToken;
    }

    private String generateFullToken(String token) {
        String bearer = "Bearer ";
        return bearer + token;
    }

    private JwtToken createJwtToken(String fullToken, Long userId) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(fullToken);
        jwtToken.setTokenIssuedTime(EXPIRE_TIME);
        jwtToken.setUserId(userId);
        return jwtToken;
    }

    @Override
    public String findUserEmailByToken(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String email = claimsJws
                .getBody()
                .getSubject();
        return email;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }
}
