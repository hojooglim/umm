package com.example.umm.user.service;

import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.dto.KakaoUserInfoDto;
import com.example.umm.user.dto.NaverUserInfoDto;
import com.example.umm.user.entity.User;
import com.example.umm.user.entity.UserRoleEnum;
import com.example.umm.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NaverService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public String naverLogin(String code, String state) throws JsonProcessingException {
        String accessToken = getToken(code, state);
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);

        User naverUser = registerNaverUserIfNeeded(naverUserInfo);
        String createtoken = jwtUtil.createToken(naverUser.getEmail(),naverUser.getRole());

        return createtoken;
    }

    private String getToken(String code, String state) throws JsonProcessingException {
// 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "");
        body.add("client_secret", "");
        body.add("code", code);
        body.add("state", state);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("response")
                .get("id").asLong();
        String nickname = jsonNode.get("response")
                .get("nickname").asText();
        String email = jsonNode.get("response")
                .get("email").asText();

        return new NaverUserInfoDto(id, nickname, email);
    }

    private User registerNaverUserIfNeeded(NaverUserInfoDto naverUserInfo) {

        Long naverId = naverUserInfo.getId();
        User naverUser = userRepository.findByNaverId(naverId).orElse(null);

        if (naverUser == null) {

            String naverEmail = naverUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(naverEmail).orElse(null);

            if (sameEmailUser != null) {
                naverUser = sameEmailUser;

                naverUser = naverUser.naverIdUpdate(naverId);
            } else {

                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                String email = naverUserInfo.getEmail();

                naverUser = new User(naverUserInfo.getNickname(), encodedPassword, email, UserRoleEnum.USER,null, naverId);
            }

            userRepository.save(naverUser);
        }
        return naverUser;

    }

}
