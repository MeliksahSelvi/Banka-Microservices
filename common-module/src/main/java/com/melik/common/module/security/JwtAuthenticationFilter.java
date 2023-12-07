package com.melik.common.module.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melik.common.module.dto.ErrorResponse;
import com.melik.common.module.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author mselvi
 * @Created 05.12.2023
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;
    private static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/register",
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

        if (!StringUtils.hasText(token) || jwtTokenService.isTokenExpired(token)) {
            arrangementResponse(request, response, filterChain);
            return;
        }

        JwtUserDetails userDetails = getJwtUserDetailsFromToken(token);

        if (userDetails == null) {
            arrangementResponse(request, response, filterChain);
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

    private void arrangementResponse(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        returnErrorResponse(response);
        filterChain.doFilter(request, response);
    }

    private JwtUserDetails getJwtUserDetailsFromToken(String token) throws JsonProcessingException {
        String jwtUserDetailsAsStr = jwtTokenService.findUserDetailAsStrByToken(token);
        return objectMapper.readValue(jwtUserDetailsAsStr, JwtUserDetails.class);
    }

    private void returnErrorResponse(HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .errorDate(new Date())
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
