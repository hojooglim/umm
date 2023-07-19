package com.example.umm.umm.dto;

import com.example.umm.comment.dto.CommentResponseDto;
import com.example.umm.umm.entity.Umm;
import lombok.Getter;

import java.util.List;

@Getter
public class UmmResponseDto {
    String image;
    String contents;
    int LikeItUmmCount;
    private List<CommentResponseDto> commentList;

    public UmmResponseDto(Umm umm) {
        this.image=umm.getImage();
        this.contents=umm.getContents();
        this.LikeItUmmCount=umm.getLikeItList().size();
        this.commentList=umm.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
