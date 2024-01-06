package com.melik.account.service.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.account.service.common.exception.AccountDomainBusinessException;
import com.melik.account.service.common.rest.ErrorResponse;
import com.melik.account.service.security.credentials.JwtUserDetails;
import com.melik.account.service.security.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final ObjectMapper objectMapper;
    private static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean isSecureEndpoint = servletPathSecureEndpoint(request);
        if (!isSecureEndpoint) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if (!StringUtils.hasText(token) || jwtTokenUtil.isTokenExpired(token)) {
            arrangementResponse(response);
            filterChain.doFilter(request, response);
            return;
        }

        JwtUserDetails userDetails = getJwtUserDetailsFromToken(token);

        if (userDetails == null) {
            arrangementResponse(response);
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationToContext(userDetails);

        filterChain.doFilter(request, response);
    }

    private boolean servletPathSecureEndpoint(HttpServletRequest request) {
        return openApiEndpoints.stream()
                .noneMatch(s -> request.getServletPath().startsWith(s));
    }

    private String getToken(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");

        String token = null;
        if (StringUtils.hasText(fullToken)) {
            String bearerStr = "Bearer ";

            if (fullToken.startsWith(bearerStr)) {
                token = fullToken.substring(bearerStr.length());
            }
        }
        return token;
    }

    private void arrangementResponse(HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        returnErrorResponse(response);
    }

    private JwtUserDetails getJwtUserDetailsFromToken(String token){
        String jwtUserDetailsAsStr = jwtTokenUtil.findUserDetailAsStrByToken(token);
        try {
            return objectMapper.readValue(jwtUserDetailsAsStr, JwtUserDetails.class);
        } catch (JsonProcessingException e) {
            log.error("Could not create JwtUserDetails json!", e);
            throw new AccountDomainBusinessException("Could not create JwtUserDetails json!", e);
        }
    }

    private void returnErrorResponse(HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Valid Token Necessary")
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void setAuthenticationToContext(JwtUserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
