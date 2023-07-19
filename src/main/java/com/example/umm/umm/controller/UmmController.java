package com.example.umm.umm.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmRequestDto;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.service.UmmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UmmController {
    private final UmmService ummService;

    @PostMapping("/umm")
    public void create(@AuthenticationPrincipal UserDetailsImpl userDetails, UmmRequestDto ummRequestDto){
         ummService.create(userDetails,ummRequestDto);
    }

    @PutMapping("/umm/{umm_id}")
    public void update(@PathVariable Long umm_id, @AuthenticationPrincipal UserDetailsImpl userDetails, UmmRequestDto ummRequestDto){
        ummService.update(umm_id,userDetails,ummRequestDto);
    }

    @DeleteMapping("/umm/{umm_id}")
    public void delete(@PathVariable Long umm_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        ummService.delete(umm_id,userDetails);
    }

    @GetMapping("/umm/{umm_id}")
    public UmmResponseDto getUmm(@PathVariable Long umm_id){
       return ummService.getUmm(umm_id);
    }

    @GetMapping("/umm")
    public List<UmmResponseDto> getUmmList(){
        return ummService.getUmmList();
    }


}
