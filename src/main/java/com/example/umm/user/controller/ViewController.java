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
import java.util.List;

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
    public void updateProfileDefault(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        updateProfile(userDetails.getUser().getId(), model);
    }

    @GetMapping("/updateProfile/{user_id}")
    public String updateProfile(@PathVariable Long user_id, Model model){
        ProfileResponseDto profile = userService.findUserIdProfile(user_id);
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

        String jwtToken = kakaoService.kakaoLogin(code);
        String token = URLEncoder.encode(jwtToken, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");
        response.addCookie(cookie);

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

    @GetMapping("/block")
    public String block() {
        return "block"; // blocked.html을 찾아서 렌더링
    }

}
