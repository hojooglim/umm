package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileRequestDto;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.dto.SignupRequestDto;
import com.example.umm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    @ResponseBody
    public String signup(@RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
        return "redirect:/user/login-page";
    }
    @PutMapping("/profile")
    public ProfileResponseDto editIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto requestDto){
        return userService.updateProfile(userDetails, requestDto);
    }

}
