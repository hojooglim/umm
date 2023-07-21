package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.*;
import com.example.umm.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public String signup(@RequestBody @Valid SignupRequestDto requestDto){
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

    @PostMapping("/password")
    public void checkPassword(HttpServletResponse res, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CheckPasswordRequestDto requestDto){
        try{
            userService.checkPassword(userDetails, requestDto);
            res.setStatus(200);
        }catch(Exception e){
            res.setStatus(400);
        }
    }

    @PutMapping("/password")
    public void editPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EditPasswordRequestDto requestDto){
        userService.editPassword(userDetails,requestDto);
    }

}
