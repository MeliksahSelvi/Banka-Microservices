package com.melik.user.service.user;

import com.melik.user.service.common.DomainComponent;
import com.melik.user.service.common.exception.UserDomainBusinessException;
import com.melik.user.service.common.usecase.UseCaseHandler;
import com.melik.user.service.user.entity.User;
import com.melik.user.service.user.port.CachePort;
import com.melik.user.service.user.port.JsonPort;
import com.melik.user.service.user.port.TokenPort;
import com.melik.user.service.user.port.UserPort;
import com.melik.user.service.user.usecase.JwtToken;
import com.melik.user.service.user.usecase.UserAuthenticate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 06.01.2024
 */

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class UserLoginUseCaseHandler implements UseCaseHandler<JwtToken, UserAuthenticate> {

    @Value("${bank.jwt.token.key}")
    private String tokenKey;

    private final UserPort userPort;
    private final TokenPort tokenPort;
    private final CachePort cachePort;
    private final JsonPort<JwtToken> jsonPort;

    @Override
    public JwtToken handle(UserAuthenticate userAuthenticate) {
        Optional<User> userOptional = userPort.retrieveByEmail(userAuthenticate.getEmail());
        if (userOptional.isEmpty()) {
            log.error("User Not Found By Email! {}", userAuthenticate.getEmail());
            throw new UserDomainBusinessException("User Not Found By Email! " + userAuthenticate.getEmail());
        }
        JwtToken jwtToken = tokenPort.generateToken(userOptional.get());
        String jwtTokenAsStr = jsonPort.serialize(jwtToken);
        cachePort.writeValue(tokenKey, jwtTokenAsStr);
        log.info("Token writed to Cache");
        return jwtToken;
    }
}
