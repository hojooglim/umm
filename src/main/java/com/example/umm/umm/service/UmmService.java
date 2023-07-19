package com.example.umm.umm.service;

import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.entity.Umm;
import com.example.umm.umm.repository.UmmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UmmService {

    private final UmmRepository ummRepository;

    public UmmResponseDto createUmm(UmmRequestDto ummRequestDto) {
        Umm umm = new Umm(ummRequestDto);
        Umm saveUmm = ummRepository.save(umm);
        UmmResponseDto ummResponseDto = new UmmResponseDto(saveUmm);
        return ummResponseDto;
    }


    public Long updateUmm(Long ummId, UmmRequestDto ummRequestDto) {
        Umm umm = findUmm(ummId);
        umm.updateUmm(ummRequestDto);
        return ummId;
    }


    public Long deleteUmm(Long ummId) {
        Umm umm = findUmm(ummId);
        ummRepository.delete(umm);
        return ummId;
    }


    public List<UmmResponseDto> getAllUmm() {
        List<UmmResponseDto> getAllUmm = ummRepository.findAll().stream().map(UmmResponseDto::new).toList();
        return getAllUmm;
    }


    public UmmResponseDto getUmm(Long ummId) {
        Umm umm = findUmm(ummId);
        return new UmmResponseDto(umm);
    }


    private Umm findUmm(Long ummId) {
        return ummRepository.findById(ummId).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }


}
