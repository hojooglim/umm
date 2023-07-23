package com.example.umm.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String refreshToken;

    public RefreshToken(String email, String refreshToken) {
        this.email=email;
        this.refreshToken=refreshToken;
    }

    public void validateSameToken(String refreshToken) {
        this.refreshToken=refreshToken;
    }
}
