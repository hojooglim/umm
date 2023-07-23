package com.example.umm.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Password(String encodePassword, User user) {
        this.password=encodePassword;
        this.user=user;
    }
}
