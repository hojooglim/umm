package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final UserService userService;



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

}
