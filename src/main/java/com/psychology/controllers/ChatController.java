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

        chatMessages.add(new Message("Ви", message));


        String response = getAutoResponse(message);
        chatMessages.add(new Message("Психолог", response));

        return "redirect:/chat/" + id;
    }

    private String getAutoResponse(String message) {
        message = message.toLowerCase();

        if (message.contains("стрес")) {
            return "Здається, ви переживаєте складний період. Хочете поговорити про це детальніше?";
        } else if (message.contains("тривога") || message.contains("паніка")) {
            return "Тривога — це природна реакція. Чи знаєте ви, що її можна контролювати за допомогою дихальних практик?";
        } else if (message.contains("депресія")) {
            return "Важливо памʼятати, що ви не самі. Я тут, щоб вас підтримати.";
        } else if (message.contains("втома") || message.contains("вигорання")) {
            return "Ви, ймовірно, перевантажені. Коли ви востаннє відпочивали?";
        } else if (message.contains("самотність")) {
            return "Самотність може бути важкою. Хочете розповісти більше про свої почуття?";
        } else {
            return "Я вас чую. Розкажіть трохи більше, щоб отримати якісну консультацію.";
        }
    }
}
