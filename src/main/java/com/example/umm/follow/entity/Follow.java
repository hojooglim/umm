package com.example.umm.follow.entity;

import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User followingUser;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Follow(User followingUser, User user) {
        this.followingUser=followingUser;
        this.user=user;
    }
}
