package com.example.umm.comment.entity;

import com.example.umm.comment.dto.CommentRequestDto;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Umm umm;

    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails, Umm umm) {
        this.comment= requestDto.getComment();
        this.user=userDetails.getUser();
        this.umm=umm;
    }


    public void update(CommentRequestDto requestDto) {
        this. comment = requestDto.getComment();
    }
}
