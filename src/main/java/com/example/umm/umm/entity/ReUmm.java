package com.example.umm.umm.entity;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReUmm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "umm_id")
    private Umm umm;


    public ReUmm(Umm umm, UserDetailsImpl userDetails) {
        this.umm=umm;
        this.user=userDetails.getUser();
    }
}
