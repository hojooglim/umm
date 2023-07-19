package com.example.umm.user.entity;

import com.example.umm.follow.entity.Follow;
import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.dto.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;
    private String password;
    private String nickname;
    private String myComment;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @OneToMany(mappedBy = "user")
    private List<Follow> followList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Umm> ummList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ReUmm> ReUmmList = new ArrayList<>();

    public User(String email, String password, String nickname, UserRoleEnum role) {
        this.email=email;
        this.password=password;
        this.nickname=nickname;
        this.role=role;
    }

    public User updateProfile(ProfileRequestDto requestDto) {
        this.nickname=requestDto.getNickname();
        this.myComment=requestDto.getMyComment();
        return new User();
    }
}
