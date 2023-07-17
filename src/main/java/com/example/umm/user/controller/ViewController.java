package com.example.umm.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String home(){return "index";}
    @GetMapping("user/login-page")
    public String login(){
        return "login";
    }

    @GetMapping("user/signup-page")
    public String signup(){
        return "signup";
    }
}
