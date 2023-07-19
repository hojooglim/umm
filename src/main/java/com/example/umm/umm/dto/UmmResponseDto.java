package com.example.umm.umm.dto;

import com.example.umm.comment.dto.CommentResponseDto;
import com.example.umm.umm.entity.Umm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class UmmResponseDto {
    Long id;
    String nickname;
    String image;
    String contents;
    LocalDate createdAt;
    int LikeItUmmCount;
    int commentCount;
    private List<CommentResponseDto> commentList;

    public UmmResponseDto(Umm umm) {
        this.id=umm.getId();
        this.nickname=umm.getUser().getNickname();
        this.image=umm.getImage();
        this.contents=umm.getContents();
        this.createdAt=umm.getCreatedAt().toLocalDate();
        this.LikeItUmmCount=umm.getLikeItList().size();
        this.commentList=umm.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.commentCount=umm.getCommentList().size();
    }
}
