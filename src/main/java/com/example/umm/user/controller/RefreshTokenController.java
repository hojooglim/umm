package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;


    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        validateExistHeader(request);
        String email = userDetails.getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        String refreshToken = jwtUtil.getRefreshTokenFromCookie(request);


        refreshTokenService.checkToken(refreshToken, email);

        String accessToken = jwtUtil.createToken(email,role);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.TOKEN_HEADER, refreshToken);
    }

    private void validateExistHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("RefreshToken");
        if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
            throw new IllegalArgumentException();
        }
    }
}
