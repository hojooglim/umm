package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserService userService;

    @GetMapping("/")
    public String home(){return "index";}
    @GetMapping("/user/profile")
    public String profile(){
        return "profile";
    }
    @GetMapping("/user/updateProfile")
    public String updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        ProfileResponseDto profile = userService.findUserProfile(userDetails);
        model.addAttribute("profile",profile);
        return "updateProfile";
    }
    @GetMapping("/user/login-page")
    public String login(){
        return "login";
    }

    @GetMapping("/user/signup-page")
    public String signup(){
        return "signup";
    }

}
