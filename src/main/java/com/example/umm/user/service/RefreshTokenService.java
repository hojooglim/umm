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

    @Transactional
    public void saveRefreshToken(String email, String refreshToken){
        //로그인 다시 할때 쿠키에서 리프레쉬 토큰 가져왔고
        String Token=jwtUtil.substringToken(refreshToken);
        //저장되어있는지 확인해
        RefreshToken saveRefreshToken = refreshTokenRepository.findByEmail(email);

        if (saveRefreshToken == null) {
            //저장안되어있으면 저장하고
            refreshTokenRepository.save(new RefreshToken(email,Token));
        } else {
            //저장 되어있으면 토큰만 없데이트
            saveRefreshToken.update(Token);
        }

    }

    @Transactional
    public void checkToken(String refreshToken, String email) {

        RefreshToken savedToken = refreshTokenRepository.findByEmail(email);

        if (!jwtUtil.validateToken(savedToken.getRefreshToken())) {
            refreshTokenRepository.delete(savedToken);
        }


        savedToken.update(refreshToken);


    }
}
