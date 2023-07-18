package com.example.umm.user.dto;

import com.example.umm.follow.dto.FollowingUserDto;
import com.example.umm.follow.entity.Follow;
import com.example.umm.user.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProfileResponseDto {

    private String nickname;
    private String myComment;
    private int followUsers;
    private List<FollowingUserDto> followingUserDtoList;

    public ProfileResponseDto(User user) {
        this.nickname= user.getNickname();
        this.myComment=user.getMyComment();
        this.followUsers=user.getFollowList().size();
        this.followingUserDtoList = new ArrayList<>();
        for (Follow follow : user.getFollowList()) {
            FollowingUserDto followingUserDto = new FollowingUserDto(follow);
            this.followingUserDtoList.add(followingUserDto);
        }
    }
}
