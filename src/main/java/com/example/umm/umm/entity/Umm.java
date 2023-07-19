package com.example.umm.umm.entity;


import com.example.umm.umm.dto.UmmRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Umm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String image;

    @Column
    String contents;


    public Umm(UmmRequestDto ummRequestDto) {
        this.image = ummRequestDto.getImage();
        this.contents = ummRequestDto.getContents();
    }


    public void updateUmm(UmmRequestDto ummRequestDto) {
        this.image = ummRequestDto.getImage();
        this.contents = ummRequestDto.getContents();
    }
}
