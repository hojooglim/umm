package com.example.umm.follow.service;

import com.example.umm.follow.entity.Follow;
import com.example.umm.follow.repository.FollowRepository;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.entity.User;
import com.example.umm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void following(UserDetailsImpl userDetails, Long userId) {
        User followingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        Optional<Follow> checkFollow = followRepository.findByFollowingUserAndUser(followingUser,userDetails.getUser());
        checkFollow.ifPresentOrElse(
                likeIt -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    followRepository.delete(checkFollow.get());
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    followRepository.save(new Follow(followingUser, userDetails.getUser()));
                }
        );

    }



}
