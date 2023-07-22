package com.example.umm.user.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailyUmmCountDTO {
    private LocalDate joinDate;
    private Long ummCount;

    public DailyUmmCountDTO(LocalDate joinDate, Long ummCount) {
        this.joinDate = joinDate;
        this.ummCount = ummCount;
    }
}
