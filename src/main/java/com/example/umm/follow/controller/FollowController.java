package com.example.umm.follow.controller;

import com.example.umm.follow.service.FollowService;
import com.example.umm.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/{user_id}")
     public void following(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long user_id){
        followService.following(userDetails,user_id);
    }
    @DeleteMapping("/follow/{user_id}")
    public void unfollowing(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long user_id) {
        followService.unfollowing(userDetails, user_id);
    }
}
