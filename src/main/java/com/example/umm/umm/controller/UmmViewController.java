package com.example.umm.umm.controller;

import com.example.umm.security.filter.UserDetailsImpl;
import com.example.umm.umm.dto.UmmResponseDto;
import com.example.umm.umm.service.UmmService;
import com.example.umm.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UmmViewController {
    private final UmmService ummService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser().getRole() == UserRoleEnum.GUEST) {
            return "block";
        }
        List<UmmResponseDto> ummList = ummService.getUmmList(userDetails);
        model.addAttribute("ummList", ummList);
        return "index";
    }

    @GetMapping("/umm")
    public String getUmm(@RequestParam(required = false) Long umm_id, Model model) {
        if (umm_id == null) {
            model.addAttribute("umm", new UmmResponseDto());
        } else {
            UmmResponseDto umm = ummService.getUmm(umm_id);
            model.addAttribute("umm", umm);
        }
        return "umm";
    }

    @GetMapping("/ummList")
    public String home(Model model) {

        List<UmmResponseDto> ummList = ummService.getAllUmmList();
        model.addAttribute("ummList", ummList);
        return "ummList";
    }

    @GetMapping("/getUmm")
    public String getOneUmm(Model model, @RequestParam(required = false) Long umm_id) {
        UmmResponseDto umm = ummService.getUmm(umm_id);
        model.addAttribute("umm", umm);
        return "getUmm";

    }

    @GetMapping("/ummScroll")
    public String ummScroll() {
        return "ummScroll";
    }
}
