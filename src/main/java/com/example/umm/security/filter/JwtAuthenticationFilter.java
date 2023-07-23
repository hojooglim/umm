package com.example.umm.security.filter;

import com.example.umm.security.dto.LoginRequestDto;
import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.jwtUtil=jwtUtil;
        this.refreshTokenService=refreshTokenService;
        setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword(), null));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String refreshToken = jwtUtil.createRefreshToken();

        refreshTokenService.saveRefreshToken(email, refreshToken);

        response.setStatus(201);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(email, role));
        response.addHeader(JwtUtil.TOKEN_HEADER, refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
