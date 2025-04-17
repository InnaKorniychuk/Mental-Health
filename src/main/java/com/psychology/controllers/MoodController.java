package com.psychology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoodController {

    private String lastMood = null;
    private String lastNote = null;

    @GetMapping("/mood")
    public String moodPage(Model model) {
        model.addAttribute("mood", lastMood);
        model.addAttribute("note", lastNote);
        return "mood-journal";
    }

    @PostMapping("/mood")
    public String saveMood(@RequestParam("mood") String mood,
                           @RequestParam("note") String note,
                           Model model) {
        lastMood = mood;
        lastNote = note;
        model.addAttribute("mood", lastMood);
        model.addAttribute("note", lastNote);
        return "mood-journal";
    }
}
