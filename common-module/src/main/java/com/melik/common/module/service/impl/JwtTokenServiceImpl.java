package com.melik.common.module.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.common.module.dto.JwtToken;
import com.melik.common.module.security.JwtUserDetails;
import com.melik.common.module.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

@Component
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${bank.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    private final ObjectMapper objectMapper;

    @Override
    public JwtToken genereteJwtToken(Long id, String email, String password) throws JsonProcessingException {

        JwtUserDetails jwtUserDetails = new JwtUserDetails(id, email, password);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String userDetailsAsStr = objectMapper.writeValueAsString(jwtUserDetails);

        String token = Jwts.builder()
                .setSubject(userDetailsAsStr)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();

        JwtToken jwtToken = createJwtToken(token, jwtUserDetails.getId());
        return jwtToken;
    }

    private JwtToken createJwtToken(String token, Long userId) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setTokenIssuedTime(EXPIRE_TIME);
        jwtToken.setUserId(userId);
        return jwtToken;
    }

    @Override
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

    @Override
    public boolean isTokenExpired(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        Date expirationDate = claimsJws.getBody().getExpiration();
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

}