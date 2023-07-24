package com.example.umm.user.entity;

import com.example.umm.comment.entity.Comment;
import com.example.umm.follow.entity.Follow;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Timestamped;
import com.example.umm.umm.entity.Umm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;
    private String password;
    private String nickname;
    private String myComment;
    private String myImage;
    private Long kakaoId;
    private Long naverId;
    private String email_code;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @OneToMany(mappedBy = "user")
    private List<Follow> followList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Umm> ummList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ReUmm> ReUmmList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> CommentList = new ArrayList<>();

    public User(String email, String password, String nickname, UserRoleEnum role) {
        this.email=email;
        this.password=password;
        this.nickname=nickname;
        this.role=role;
    }

    public User(String email, String ePw) {
        this.email=email;
        this.email_code=ePw;
    }

    public void updateProfile(String nickname, String myComment, String imageUrl) {
        this.nickname=nickname;
        this.myComment=myComment;
        this.myImage=imageUrl;
    }

    public void updateProfile(String nickname, String myComment) {
        this.nickname=nickname;
        this.myComment=myComment;
    }

    public void updatePassword(String password) {
        this.password=password;
    }

    public User(String nickname, String encodedPassword, String email, UserRoleEnum role, Long kakaoId, Long naverId) {
        this.email=email;
        this.password=encodedPassword;
        this.nickname=nickname;
        this.role=role;
        this.kakaoId=kakaoId;
        this.naverId=naverId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId=kakaoId;
        return new User();
    }

    public User naverIdUpdate(Long naverId) {
        this.naverId=naverId;
        return new User();
    }

    public void updateRole(UserRoleEnum userRoleEnum) {
        this.role=userRoleEnum;
    }
}
