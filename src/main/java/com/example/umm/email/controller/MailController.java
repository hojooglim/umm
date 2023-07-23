package com.example.umm.email.controller;

import com.example.umm.email.dto.CodeDto;
import com.example.umm.email.dto.MailDto;
import com.example.umm.email.service.MailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    @PostMapping("/user/mail")
    public String mailConfirm(@RequestBody MailDto mailDto) throws Exception {
        String code = mailService.sendSimpleMessage(mailDto.getEmail());
        System.out.println("인증코드 : " + code);
        return code;
    }

    //인증번호 비교
    @PostMapping("/user/mailCode")
    public void mailCodeCompare(@RequestBody CodeDto codeDto, HttpServletResponse res){
        try{
            mailService.compareCode(codeDto);
        }catch(Exception e){
            res.setStatus(400);
        }

    }
}