package com.psychology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {
    @GetMapping("/info")
    public String information(){
        return "information-page";
    }
    @GetMapping("/panic")
    public String panic(){
        return "panic-attack";
    }
}
