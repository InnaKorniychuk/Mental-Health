package com.psychology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pre-register")
@SessionAttributes("preRegisterData")
public class PreRegisterController {

    @ModelAttribute("preRegisterData")
    public Map<String, String> preRegisterData() {
        return new HashMap<>();
    }

    @GetMapping("/start")
    public String start() {
        return "pre-register/question1";
    }

    @PostMapping("/question2")
    public String question2(@RequestParam String reason,
                            @ModelAttribute("preRegisterData") Map<String, String> data) {
        data.put("reason", reason);
        return "pre-register/question2";
    }

    @PostMapping("/question3")
    public String question3(@RequestParam String anxiety,
                            @ModelAttribute("preRegisterData") Map<String, String> data) {
        data.put("anxiety", anxiety);
        return "pre-register/question3";
    }

    @PostMapping("/summary")
    public String summary(@RequestParam String trust,
                          @ModelAttribute("preRegisterData") Map<String, String> data) {
        data.put("trust", trust);
        return "redirect:/register";
    }
}
