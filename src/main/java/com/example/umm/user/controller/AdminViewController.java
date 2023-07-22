package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminViewController {
    private final AdminService adminService;


    @GetMapping("/admin")
    public String userList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        List<ProfileResponseDto> profileResponseDtoList = adminService.getUsersInfo(userDetails);
        model.addAttribute("userList",profileResponseDtoList);
        return "admin";
    }

}
