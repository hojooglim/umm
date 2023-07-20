package com.example.umm.umm.dto;

import com.example.umm.comment.dto.CommentResponseDto;
import com.example.umm.umm.entity.ReUmm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReUmmResponseDto {
    Long id;
    Long user_id;
    String nickname;
    String image;
    String contents;
    String myImage;
    LocalDate createdAt;
    int LikeItUmmCount;
    int commentCount;
    private List<CommentResponseDto> commentList;

    public ReUmmResponseDto(ReUmm reUmm) {
        this.id=reUmm.getUmm().getId();
        this.user_id=reUmm.getUser().getId();
        this.nickname=reUmm.getUmm().getUser().getNickname();
        this.image=reUmm.getUmm().getImage();
        this.myImage=reUmm.getUmm().getUser().getMyImage();
        this.contents=reUmm.getUmm().getContents();
        this.createdAt=reUmm.getUmm().getCreatedAt().toLocalDate();
        this.LikeItUmmCount=reUmm.getUmm().getLikeItList().size();
        this.commentList=reUmm.getUmm().getCommentList().stream().map(CommentResponseDto::new).toList();
        this.commentCount=reUmm.getUmm().getCommentList().size();
    }
}
