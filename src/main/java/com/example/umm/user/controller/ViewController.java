package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.security.util.JwtUtil;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.service.KakaoService;
import com.example.umm.user.service.NaverService;
import com.example.umm.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserService userService;
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @GetMapping("/profile")
    public void profileDefault(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        profile(userDetails.getUser().getId(), model);
    }

    @GetMapping("/profile/{user_id}")
    public String profile(@PathVariable Long user_id, Model model) {

        ProfileResponseDto profile = userService.findUserIdProfile(user_id);

        model.addAttribute("profile",profile);

        return "profile";
    }

    @GetMapping("/reumm")
    public void reUmmDefault(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        profile(userDetails.getUser().getId(), model);
    }

    @GetMapping("/reumm/{user_id}")
    public String reUmm(@PathVariable Long user_id, Model model) {

        ProfileResponseDto profile = userService.findUserIdProfile(user_id);

        model.addAttribute("profile",profile);

        return "reumm";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        ProfileResponseDto profile = userService.findUserProfile(userDetails);
        model.addAttribute("profile",profile);
        return "updateProfile";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(){
        return "updatePassword";
    }

    @GetMapping("/user/login-page")
    public String login(){
        return "login";
    }

    @GetMapping("/user/signup-page")
    public String signup(){
        return "signup";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        //순서가 1)로그인을 하면 2)확인코드가 넘어오고
        //그 3)확인코드를 가지고 토큰을 요청하고
        // 4)토큰을 전달 받으면
        //그 5)토큰을 다시 보내면
        // 6)사용자 정보를 받아올수 있음.
        //7) 사용자 정보로 회원가입 시켜주고
        // 8) jwt토큰 발급해 주면됨. (로그인을 한거니깐)

        //ㅋㅏ카오 서버에서 넘어오는 코드는 리퀘스트 파람으로 받고
        //jwt 토큰을 만들어서 쿠키에 넣어서 주는거
        //원래는 헤더에 넣어서 보내줫지
        String jwtToken = kakaoService.kakaoLogin(code);
        String token = URLEncoder.encode(jwtToken, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");
        response.addCookie(cookie);
        //문제 쿠키에 토큰 넣어주는 것 까지 확인 but 베어럴이 안들어감 ;;;;;
        //해결 url인코더로 인코딩해서 토큰에 배어럴 넘겨주니깐 됫음
        //일단 자바스크립트에서 토큰 검사할때 베어럴 추가되는거 안먹음.
        return "redirect:/";
    }

    @GetMapping("/user/naver/callback")
    public String naverLogin(@RequestParam String code,@RequestParam String state, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {

        String jwtToken = naverService.naverLogin(code, state);
        String token = URLEncoder.encode(jwtToken, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
