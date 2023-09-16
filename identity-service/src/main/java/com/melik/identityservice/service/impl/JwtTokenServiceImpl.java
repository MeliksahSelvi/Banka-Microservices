package com.melik.identityservice.service.impl;

import com.melik.identityservice.dto.JwtToken;
import com.melik.identityservice.security.JwtUserDetails;
import com.melik.identityservice.service.JwtTokenService;
import com.melik.identityservice.util.CacheUtil;
import com.melik.identityservice.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Component
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${bank.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${bank.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    private final CacheUtil cacheUtil;

    @Override
    public JwtToken genereteJwtToken(Authentication authentication) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        String token = Jwts.builder()
                .setSubject(Long.toString(jwtUserDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();

        String fullToken = generateFullToken(token);

        JwtToken jwtToken = createJwtToken(fullToken, jwtUserDetails.getId());

        return jwtToken;
    }

    private String generateFullToken(String token) {
        String bearer = Constants.BEARER_TOKEN;
        String fullToken = bearer + token;
        return fullToken;
    }

    private JwtToken createJwtToken(String fullToken, Long userId) {
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(fullToken);
        jwtToken.setTokenIssuedTime(EXPIRE_TIME);
        jwtToken.setUserId(userId);

        return jwtToken;
    }

    @Override
    public Long findUserIdByToken(String token) {

        Jws<Claims> claimsJws = parseToken(token);

        String userIdStr = claimsJws
                .getBody()
                .getSubject();

        Long userId = Long.parseLong(userIdStr);
        return userId;
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
        return claimsJws;
    }

    @Override
    public boolean validateToken(String token) {

        boolean isValid;
        try {
            isValid = !isTokenExpired(token);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

    private boolean isTokenExpired(String token) {

        JwtToken jwtToken = cacheUtil.readTokenMap(token);

        Long userIdByToken = findUserIdByToken(token);

        if (jwtToken == null) {
            return true;
        }

        long expireMs = EXPIRE_TIME - (Instant.now().getEpochSecond() - jwtToken.getTokenIssuedTime());

        if (expireMs > 0 && userIdByToken.equals(jwtToken.getUserId())) {

            jwtToken.setTokenIssuedTime(Instant.now().getEpochSecond());
            cacheUtil.writeTokenMap(jwtToken);
            return false;
        } else {
            cacheUtil.deleteFromTokenMap(token);
            return true;
        }
    }
}
