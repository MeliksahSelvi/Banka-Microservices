package com.melik.identityservice.security;

import com.melik.identityservice.service.JwtTokenService;
import com.melik.identityservice.service.JwtUserDetailsService;
import com.melik.identityservice.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        Long userId = jwtTokenService.findUserIdByToken(token);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUserId(userId);

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);//token bu sistemden mi üretildi kontrolü için işimize yarıyor.
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");

        String token = null;
        if (StringUtils.hasText(fullToken)) {
            String bearer = Constants.BEARER_TOKEN;

            if (fullToken.startsWith(bearer)) {
                token = fullToken.substring(bearer.length());
            }
        }
        return token;
    }
}
