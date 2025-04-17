package com.psychology.controllers;

import com.psychology.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String dateOfBirth
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error=passwords";
        }

        try {
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
            boolean success = userService.save(username, email, password, dob);

            if (!success) {
                return "redirect:/register?error=exists";
            }

            return "redirect:/mood";
        } catch (Exception e) {
            return "redirect:/register?error=invalidDate";
        }
    }
}
