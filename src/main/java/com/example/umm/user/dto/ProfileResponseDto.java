package com.example.umm.user.dto;

import com.example.umm.follow.dto.FollowingUserDto;
import com.example.umm.umm.dto.ReUmmResponseDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileResponseDto {

    private String nickname;
    private String myComment;
    private int followUsers;
    private List<FollowingUserDto> followingUserDtoList;
    private List<UmmResponseDto> ummList;
    private List<ReUmmResponseDto> ReUmmList;

    public ProfileResponseDto(User user) {
        this.nickname= user.getNickname();
        this.myComment=user.getMyComment();
        this.followUsers=user.getFollowList().size();
        this.followingUserDtoList=user.getFollowList().stream().map(FollowingUserDto::new).toList();
        this.ummList=user.getUmmList().stream().map(UmmResponseDto::new).toList();
        this.ReUmmList=user.getReUmmList().stream().map(ReUmmResponseDto::new).toList();
    }
}
