package com.example.umm.follow.dto;

import com.example.umm.follow.entity.Follow;
import lombok.Getter;

@Getter
public class FollowingUserDto {
    private Long id;
    private String nickname;
    private String myImage;

    public FollowingUserDto(Follow follow) {
        this.id=follow.getFollowingUser().getId();
        this.nickname=follow.getFollowingUser().getNickname();
        this.myImage=follow.getFollowingUser().getMyImage();
    }
}
