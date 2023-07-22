package com.example.umm.user.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.repository.UmmRepository;
import com.example.umm.user.dto.DailySignupCountDTO;
import com.example.umm.user.dto.DailyUmmCountDTO;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final UmmRepository ummRepository;


    @GetMapping("/admin")
    public String userList(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        List<ProfileResponseDto> profileResponseDtoList = adminService.getUsersInfo(userDetails);
        List<UmmResponseDto> ummResponseDtoList = adminService.getUmms(userDetails);
        model.addAttribute("userList",profileResponseDtoList);
        model.addAttribute("ummList",ummResponseDtoList);

        List<DailySignupCountDTO> dailySignupCountList = userRepository.getDailySignupCount();

        model.addAttribute("dailySignupCountList", dailySignupCountList);

        List<DailyUmmCountDTO> dailyUmmCountList = ummRepository.getDailyUmmCount();

        model.addAttribute("dailyUmmCountList", dailyUmmCountList);

        return "admin";
    }



}
