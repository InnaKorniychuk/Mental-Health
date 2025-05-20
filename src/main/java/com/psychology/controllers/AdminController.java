package com.psychology.controllers;

import com.psychology.model.Session;
import com.psychology.model.SessionStatus;
import com.psychology.repository.SessionRepository;
import com.psychology.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

// AdminSessionController.java
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SessionRepository sessionRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    public AdminController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    @GetMapping("/info")
    public String showInfo() {
        return "admin/info";
    }
    @GetMapping("/sessions")
    public String showSessions(Model model, Principal principal) {
//        String email = principal.getName();
//        List<Session> sessions = sessionService.getSessionsForPhysician(email);
//        model.addAttribute("sessions", sessions);
        List<Session> sessions = sessionRepository.findAll();

        // 🟡 Ось ця частина додає українські назви статусів
        Map<SessionStatus, String> statusMap = Map.of(
                SessionStatus.PENDING, "Очікує підтвердження",
                SessionStatus.APPROVED, "Підтверджено",
                SessionStatus.CANCELLED, "Скасовано"
        );
        model.addAttribute("sessions", sessions);
        model.addAttribute("statusMap", statusMap);
        return "admin/admin-session";
    }
//    @GetMapping
//    public String viewSessions(Model model) {
//        List<Session> sessions = sessionRepository.findAll(); // Перевір чи тут підтягується physician
//        model.addAttribute("sessions", sessions);
//        return "admin/admin-session";
//    }

    @PostMapping("/approve")
    public String approveSession(@RequestParam Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Сесія не знайдена"));
        session.setStatus(SessionStatus.APPROVED);
        sessionRepository.save(session);
        return "redirect:/admin/sessions";
    }

    @PostMapping("/cancel")
    public String cancelSession(@RequestParam Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Сесія не знайдена"));
        session.setStatus(SessionStatus.CANCELLED);
        sessionRepository.save(session);
        return  "redirect:/admin/sessions";
    }
}

//@Controller
//public class AdminController {
//
//    @Autowired
//    private SessionService sessionService;
//
//    @GetMapping("/admin/sessions")
//    public String viewPendingSessions(Model model) {
//        model.addAttribute("sessions", sessionService.getPendingSessions());
//        return "admin/admin-session";
//    }
//
//    @PostMapping("/admin/sessions/approve")
//    public String approveSession(@RequestParam Long sessionId) {
//        sessionService.approveSession(sessionId);
//        return "redirect:/admin/sessions";
//    }
//
//    @PostMapping("/admin/sessions/cancel")
//    public String cancelSession(@RequestParam Long sessionId) {
//        sessionService.cancelSession(sessionId);
//        return "redirect:/admin/sessions";
//    }
//}
