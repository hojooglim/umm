package com.example.umm.likeit.entity;


import com.example.umm.comment.entity.Comment;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikeIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "umm_id")
    private Umm umm;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public LikeIt(Umm umm, User user) {
        this.umm=umm;
        this.user=user;
    }
    public LikeIt(Comment comment, User user) {
        this.comment=comment;
        this.user=user;
    }
}
