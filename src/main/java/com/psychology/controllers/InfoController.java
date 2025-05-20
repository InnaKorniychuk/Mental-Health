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
    @GetMapping("/topics")
    public String topics(){
        return "topics";
    }
    @GetMapping("/tests")
    public String tests(){
        return "tests";
    }
    @GetMapping("/stress")
    public String stress(){
        return "articles/stress";
    }
    @GetMapping("/anxiety")
    public String anxiety(){
        return "articles/anxiety";
    }
    @GetMapping("/tests/anxiety")
    public String testAnxiety(){
        return "tests/anxiety";
    }
    @GetMapping("/tests/stress")
    public String testStress(){
        return "tests/stress";
    }
    @GetMapping("/tests/self-esteem")
    public String testsSelfEsteem(){
        return "tests/self-esteem";
    }
    @GetMapping("/mindfulness")
    public String mindfulness(){
        return "articles/mindfulness";
    }
    @GetMapping("/self-esteem")
    public String selfEsteem(){
        return "articles/self-esteem";
    }

}
