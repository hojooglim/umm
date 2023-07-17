package com.example.umm.security.filter;


import com.example.umm.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = JwtUtil.getTokenFromCookie(req);

        if (StringUtils.hasText(tokenValue)) {
            tokenValue = JwtUtil.substringToken(tokenValue);
            if (!JwtUtil.validateToken(tokenValue)) {
                return;
            }
            try {
                JwtUtil.getAuthentication(tokenValue);
            } catch (Exception e) {
                return;
            }
        }
        filterChain.doFilter(req, res);

    }
}
