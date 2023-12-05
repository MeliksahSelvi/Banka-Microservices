package com.melik.common.module.service.impl;

import com.google.gson.Gson;
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

    @Override
    public JwtToken genereteJwtToken(Long id, String email, String password) {

        JwtUserDetails jwtUserDetails = new JwtUserDetails(id, email, password);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null,jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String userDetailsAsStr = new Gson().toJson(jwtUserDetails, JwtUserDetails.class);
        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(userDetailsAsStr)
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