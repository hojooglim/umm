package com.example.umm.comment.dto;


import com.example.umm.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String nickname;
    private String comment;
    public CommentResponseDto(Comment comment) {
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getComment();
    }
}
