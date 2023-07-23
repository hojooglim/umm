package com.example.umm.comment.dto;


import com.example.umm.comment.entity.Comment;
import com.example.umm.likeit.entity.LikeIt;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String comment;
    int likeItCommentCount;

    public CommentResponseDto(Comment comment) {
        this.id=comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getComment();
        this.likeItCommentCount=comment.getLikeItList().size();
    }
}
