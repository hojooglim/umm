package com.example.umm.umm.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.service.UmmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UmmController {
    private final UmmService ummService;

    @PostMapping("/umm")
    public void create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @RequestParam(value = "image", required = false) MultipartFile image,
                       @RequestParam(value = "contents") String contents) throws IOException {
        ummService.create(userDetails, image, contents);
    }

    @PutMapping("/umm/{umm_id}")
    public void update(@PathVariable Long umm_id, @AuthenticationPrincipal UserDetailsImpl userDetails,
                       @RequestParam(value = "image", required = false) MultipartFile image,
                       @RequestParam(value = "contents") String contents) throws IOException {
        ummService.update(umm_id, userDetails, image, contents);
    }

    @DeleteMapping("/umm/{umm_id}")
    public void delete(@PathVariable Long umm_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ummService.delete(umm_id, userDetails);
    }

    @PostMapping("/re-Umm/{umm_id}")
    public void reUmm(@PathVariable Long umm_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ummService.reUmm(umm_id, userDetails);
    }

    @GetMapping("/umm/{umm_id}")
    public UmmResponseDto getUmm(@PathVariable Long umm_id) {
        return ummService.getUmm(umm_id);
    }

    @GetMapping("/ummScroll/{size}")
    public List<UmmResponseDto> ummScroll(@PathVariable int size) {
        //현재 페이지  * 보여주는 게시물 개수
        return ummService.getUmmScroll(size);
    }
}
