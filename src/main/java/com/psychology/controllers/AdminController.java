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
        List<Session> sessions = sessionRepository.findAll();

        Map<SessionStatus, String> statusMap = Map.of(
                SessionStatus.PENDING, "Pending",
                SessionStatus.APPROVED, "Approved",
                SessionStatus.CANCELLED, "Cancelled"
        );
        model.addAttribute("sessions", sessions);
        model.addAttribute("statusMap", statusMap);
        return "admin/admin-session";
    }

    @PostMapping("/approve")
    public String approveSession(@RequestParam Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session is not found"));
        session.setStatus(SessionStatus.APPROVED);
        sessionRepository.save(session);
        return "redirect:/admin/sessions";
    }

    @PostMapping("/cancel")
    public String cancelSession(@RequestParam Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session is not found"));
        session.setStatus(SessionStatus.CANCELLED);
        sessionRepository.save(session);
        return  "redirect:/admin/sessions";
    }
}
