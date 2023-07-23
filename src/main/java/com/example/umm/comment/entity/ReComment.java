package com.example.umm.comment.entity;

import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ReComment;

    @ManyToOne
    @JoinColumn(name = "Commnet_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReComment(CommentRequestDto requestDto, UserDetailsImpl userDetails, Comment comment) {
        this.ReComment=requestDto.getComment();
        this.comment=comment;
        this.user=userDetails.getUser();
    }

    public void update(CommentRequestDto requestDto) {
        this.ReComment=requestDto.getComment();
    }
}
