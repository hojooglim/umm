package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileRequestDto;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.dto.SignupRequestDto;
import com.example.umm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void editIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestParam(value = "nickname") String nickname,
                                            @RequestParam(value = "myComment") String myComment,
                                            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
         userService.updateProfile(userDetails, nickname, myComment, image);
    }

}
