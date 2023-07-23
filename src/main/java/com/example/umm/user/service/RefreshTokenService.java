package com.example.umm.user.service;

import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.entity.RefreshToken;
import com.example.umm.user.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void saveRefreshToken(String email, String refreshToken){

        refreshTokenRepository.save(new RefreshToken(email,refreshToken));
    }

    @Transactional
    public void checkToken(String refreshToken, String email) {

        RefreshToken savedToken = refreshTokenRepository.findByEmail(email);

        if (!jwtUtil.validateToken(savedToken.getRefreshToken())) {
            refreshTokenRepository.delete(savedToken);

        }

        savedToken.validateSameToken(refreshToken);


    }
}
