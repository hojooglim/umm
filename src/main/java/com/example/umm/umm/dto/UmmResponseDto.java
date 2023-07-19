package com.example.umm.umm.dto;

import com.example.umm.umm.entity.Umm;
import lombok.Getter;

@Getter
public class UmmResponseDto {
    Long id;
    String image;
    String contents;

    public UmmResponseDto(Umm saveUmm) {
        this.id = saveUmm.getId();
        this.image = saveUmm.getImage();
        this.contents = saveUmm.getContents();
    }
}
