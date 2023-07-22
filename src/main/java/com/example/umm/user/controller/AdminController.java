package com.example.umm.user.controller;


import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.user.dto.ProfileResponseDto;
import com.example.umm.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/userInfo")
    public List<ProfileResponseDto> getUsersInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.getUsersInfo(userDetails);
    }

    @GetMapping("/admin/userInfo/{user_id}")
    public ProfileResponseDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long user_id){
        return adminService.getuserInfo(userDetails,user_id);
    }

    @PutMapping("/admin/userInfo/{user_id}")
    public void updateUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam(value = "nickname") String nickname,
                               @RequestParam(value = "myComment") String myComment,
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               @PathVariable Long user_id) throws IOException {
        adminService.updateUserInfo(userDetails,user_id, nickname, myComment, image);
    }

    @DeleteMapping("/admin/userInfo/{user_id}")
    public void deleteUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long user_id){
        adminService.deleteUserInfo(userDetails,user_id);
    }

    @PutMapping("/admin/userInfo/auth/{user_id}")
    public void chageUserAuth(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long user_id){
        adminService.chageUserAuth(userDetails,user_id);
    }


    @GetMapping("/admin/umm")
    public List<UmmResponseDto> getUmms(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.getUmms(userDetails);
    }

    @GetMapping("/admin/umm/{umm_id}")
    public UmmResponseDto getUmm(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long umm_id){
        return adminService.getUmm(userDetails, umm_id);
    }

    @PutMapping("/admin/umm/{umm_id}")
    public void updateUmm(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    @RequestParam(value = "contents") String contents,
                                    @PathVariable Long umm_id) throws IOException {
        adminService.updateUmm(userDetails, umm_id, image, contents);
    }

    @DeleteMapping("/admin/umm/{umm_id}")
    public void deleteUmm(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long umm_id){
        adminService.deleteUmm(userDetails,umm_id);
    }


}
