package com.example.umm.user.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailySignupCountDTO {
    private LocalDate joinDate;
    private Long signUpCount;

    public DailySignupCountDTO(LocalDate joinDate, Long signUpCount) {
        this.joinDate = joinDate;
        this.signUpCount = signUpCount;
    }
}
