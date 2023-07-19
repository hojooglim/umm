package com.example.umm.umm.entity;

import com.example.umm.comment.entity.Comment;
import com.example.umm.likeit.entity.LikeIt;
import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Umm extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String image;

    @Column
    String contents;
    //위치?

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "umm")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "umm")
    private List<LikeIt> likeItList = new ArrayList<>();

    public Umm(UserDetailsImpl userDetails, UmmRequestDto ummRequestDto) {
        this.image= ummRequestDto.getImage();
        this.contents= ummRequestDto.getContents();
        this.user=userDetails.getUser();
    }

    public void update(UmmRequestDto ummRequestDto) {
        this.image= ummRequestDto.getImage();
        this.contents= ummRequestDto.getContents();
    }
}
