package com.example.umm.security.filter;


import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j(topic ="token 검증")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, RefreshTokenService refreshTokenService ) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService=refreshTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getTokenFromCookie(req);
        String refreshValue = jwtUtil.getRefreshTokenFromCookie(req);
        //refresh token 재발행
        if (StringUtils.hasText(tokenValue)) {
            tokenValue = jwtUtil.substringToken(tokenValue);
            refreshValue = jwtUtil.substringToken(refreshValue);

            if (jwtUtil.validateToken(tokenValue)) {

                try {
                    setAuthentication(jwtUtil.getUserInfoFromToken(tokenValue).getSubject());
                } catch (Exception e) {
                    return;
                }
                
            } else if (!jwtUtil.validateToken(tokenValue) && refreshValue != null) {
                try {
                    String email = jwtUtil.getUserInfoFromToken(tokenValue).getSubject();
                    Claims userInfo = jwtUtil.getUserInfoFromToken(tokenValue);
                    UserRoleEnum role = (UserRoleEnum) userInfo.get("auth");
                    try {
                        //refreshtoken validationcheck
                        refreshTokenService.checkToken(refreshValue,email);
                    } catch (Exception e){
                        return;
                    }

                    String newJwtToken = jwtUtil.createToken(email, role);

                    setAuthentication(jwtUtil.getUserInfoFromToken(newJwtToken).getSubject());
                } catch (Exception e) {
                    return;
                }
                
            }

        }
        filterChain.doFilter(req, res);
    }
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
