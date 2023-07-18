package com.example.umm.follow.service;

import com.example.umm.follow.entity.Follow;
import com.example.umm.follow.repository.FollowRepository;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.entity.User;
import com.example.umm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void following(UserDetailsImpl userDetails, Long userId) {
        User followingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        followRepository.save(new Follow(followingUser, userDetails.getUser()));
    }

    public void unfollowing(UserDetailsImpl userDetails, Long userId) {
        User followingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Follow follow=followRepository.findByFollowingUserAndUser(followingUser,userDetails.getUser());

        followRepository.delete(follow);
    }


}
