package com.example.umm.user.dto;

import com.example.umm.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String nickname;
    private String myComment;

    public ProfileResponseDto(User user) {
        this.nickname= user.getNickname();
        this.myComment=user.getMyComment();
    }
}
