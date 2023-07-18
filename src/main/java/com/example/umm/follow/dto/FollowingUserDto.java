package com.example.umm.follow.dto;

import com.example.umm.follow.entity.Follow;
import lombok.Getter;

@Getter
public class FollowingUserDto {
    private Long id;
    private String nickname;

    public FollowingUserDto(Follow follow) {
        this.id=follow.getFollowingUser().getId();
        this.nickname=follow.getFollowingUser().getNickname();
    }
}
