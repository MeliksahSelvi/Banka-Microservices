package com.melik.user.service.adapters.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.user.service.common.exception.UserDomainBusinessException;
import com.melik.user.service.security.credentials.JwtUserDetails;
import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.port.TokenPort;
import com.melik.user.service.user.usecase.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenAdapter implements TokenPort {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${bank.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    private final ObjectMapper objectMapper;

    @Override
    public JwtToken generateToken(User user) {
        JwtUserDetails jwtUserDetails = new JwtUserDetails(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String userDetailsAsStr = writeCredentialsToJson(jwtUserDetails);

        String token = Jwts.builder()
                .setSubject(userDetailsAsStr)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();
        log.info("Token Created for user id: {}",user.getId());
        return createJwtToken(token, jwtUserDetails.getId());
    }

    private String writeCredentialsToJson(JwtUserDetails jwtUserDetails) {
        String userDetailsAsStr;
        try {
            userDetailsAsStr = objectMapper.writeValueAsString(jwtUserDetails);
        } catch (JsonProcessingException e) {
            log.error("Could not create JwtUserDetails json!", e);
            throw new UserDomainBusinessException("Could not create JwtUserDetails json!", e);
        }
        return userDetailsAsStr;
    }

    private JwtToken createJwtToken(String token, Long userId) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setTokenIssuedTime(EXPIRE_TIME);
        jwtToken.setUserId(userId);
        return jwtToken;
    }
}
