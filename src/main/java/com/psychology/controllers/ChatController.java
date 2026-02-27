package com.psychology.controllers;

import com.psychology.model.Message;
import com.psychology.model.Physician;
import com.psychology.repository.PhysicianRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("chatMessages")
public class ChatController {

    private final PhysicianRepository physicianRepository;

    public ChatController(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    @ModelAttribute("chatMessages")
    public List<Message> chatMessages() {
        return new ArrayList<>();
    }

    @GetMapping("/chat/{id}")
    public String openChat(@PathVariable Long id, Model model, @ModelAttribute("chatMessages") List<Message> chatMessages) {
        Physician physician = physicianRepository.findById(id).orElseThrow();
        model.addAttribute("physician", physician);
        model.addAttribute("chatMessages", chatMessages);
        return "psychologists/chat";
    }

    @PostMapping("/chat/{id}")
    public String sendMessage(@PathVariable Long id,
                              @RequestParam String message,
                              @ModelAttribute("chatMessages") List<Message> chatMessages) {

        chatMessages.add(new Message("You", message));


        String response = getAutoResponse(message);
        chatMessages.add(new Message("Physician", response));

        return "redirect:/chat/" + id;
    }

    private String getAutoResponse(String message) {
        message = message.toLowerCase();

        if (message.contains("stress")) {
            return "It sounds like you’re going through a difficult time. Would you like to talk about it in more detail?";
        } else if (message.contains("anxiety") || message.contains("panic")) {
            return "Anxiety is a natural response. Did you know it can be managed with breathing techniques?";
        } else if (message.contains("depression")) {
            return "It’s important to remember that you’re not alone. I’m here to support you.";
        } else if (message.contains("fatigue") || message.contains("burnout")) {
            return "You may be feeling overwhelmed. When was the last time you had a proper rest?";
        } else if (message.contains("loneliness")) {
            return "Loneliness can be really hard. Would you like to share more about how you’re feeling?";
        } else {
            return "I hear you. Please tell me a bit more so I can better support you.";
        }
    }
}
