package com.example.umm.security.filter;


import com.example.umm.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j(topic ="token 검증")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = JwtUtil.getTokenFromCookie(req);
        log.info("꺼내고");
        if (StringUtils.hasText(tokenValue)) {
            tokenValue = JwtUtil.substringToken(tokenValue);
            log.info("자르고");
            if (!JwtUtil.validateToken(tokenValue)) {
                return;
            }
            log.info("검증");
            try {
                JwtUtil.getAuthentication(tokenValue);
            } catch (Exception e) {
                return;
            }
        }
        filterChain.doFilter(req, res);

    }
}
