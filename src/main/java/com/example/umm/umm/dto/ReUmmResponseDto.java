package com.example.umm.umm.dto;

import com.example.umm.comment.dto.CommentResponseDto;
import com.example.umm.umm.entity.ReUmm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReUmmResponseDto {
    Long id;
    String image;
    String contents;
    int LikeItUmmCount;
    private List<CommentResponseDto> commentList;

    public ReUmmResponseDto(ReUmm reUmm) {
        this.id=reUmm.getUmm().getId();
        this.image=reUmm.getUmm().getImage();
        this.contents=reUmm.getUmm().getContents();
        this.LikeItUmmCount=reUmm.getUmm().getLikeItList().size();
        this.commentList=reUmm.getUmm().getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}
