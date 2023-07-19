package com.example.umm.umm.controller;


import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.service.UmmService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/umm")
@AllArgsConstructor
public class UmmController {

    private final UmmService ummService;


    @PostMapping
    public UmmResponseDto createUmm(@RequestBody UmmRequestDto ummRequestDto) {
        return ummService.createUmm(ummRequestDto);
    }

    @PutMapping("/{umm_Id}")
    public Long updateUmm(@PathVariable Long umm_Id, @RequestBody UmmRequestDto ummRequestDto) {
        return ummService.updateUmm(umm_Id, ummRequestDto);
    }


    @DeleteMapping("/{umm_Id}")
    public Long deleteUmm(@PathVariable Long umm_Id) {
        return ummService.deleteUmm(umm_Id);
    }


    @GetMapping
    public List<UmmResponseDto> getAllUmm() {
        List<UmmResponseDto> getAllUmm = ummService.getAllUmm();
        return getAllUmm;
    }

    @GetMapping("/{umm_Id}")
    public UmmResponseDto getUmm(@PathVariable Long umm_Id) {
        UmmResponseDto ummResponseDto = ummService.getUmm(umm_Id);
        return ummResponseDto;
    }


}
