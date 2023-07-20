package com.example.umm.user.dto;

import com.example.umm.follow.dto.FollowingUserDto;
import com.example.umm.umm.dto.ReUmmResponseDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class ProfileResponseDto {

    private String nickname;
    private String myComment;
    private String myImage;
    private int followUsers;
    private List<FollowingUserDto> followingUserDtoList;
    private List<UmmResponseDto> ummList;
    private List<ReUmmResponseDto> ReUmmList;

    public ProfileResponseDto(User user) {
        this.nickname= user.getNickname();
        this.myComment=user.getMyComment();
        this.myImage=user.getMyImage();
        this.followUsers=user.getFollowList().size();
        this.followingUserDtoList=user.getFollowList().stream().map(FollowingUserDto::new).toList();
        this.ummList = new ArrayList<>();
        for (Umm umm:user.getUmmList()) {
            ummList.add(new UmmResponseDto(umm));
        }
        Collections.reverse(ummList);
        this.ReUmmList = new ArrayList<>();
        for (ReUmm reUmm:user.getReUmmList()) {
            ReUmmList.add(new ReUmmResponseDto(reUmm));
        }
        Collections.reverse(ReUmmList);
    }
}
