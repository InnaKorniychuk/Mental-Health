package com.psychology.controllers;

import com.psychology.model.Physician;
import com.psychology.model.SessionStatus;
import com.psychology.repository.PhysicianRepository;
import com.psychology.repository.SessionRepository;
import org.springframework.ui.Model;
import com.psychology.model.User;
import com.psychology.repository.UserRepository;
import com.psychology.service.SessionService;
import com.psychology.service.UserService;
import com.psychology.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PhysicianRepository physicianRepository;
    private final UserService userService;

    private final SessionService sessionService;
    @Autowired
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }


    @PostMapping("/save-time")
    public String saveAppointmentTime(@RequestParam("time") @DateTimeFormat(pattern = "HH:mm") String timeStr,
                                      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                      @RequestParam("physicianId") Long physicianId,
                                      Principal principal) {

        LocalTime selectedTime = LocalTime.parse(timeStr);
        User user = userRepository.findByEmail(principal.getName());
        System.out.println("Principal: " + principal.getName());

        Physician physician = physicianRepository.findById(physicianId).orElseThrow();
        if (user == null || physician == null) {
            throw new RuntimeException("User or physician not found");
        }

        Session session = new Session();
        session.setUser(user);
        session.setPhysician(physician);
        session.setDate(date);
        session.setTime(selectedTime);
        session.setStatus(SessionStatus.PENDING);

        sessionRepository.save(session);

        return "redirect:/payment";
    }
    @PostMapping("/change-info")
    public String changePersonalInfo(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("username") String username,
            Principal principal) {

        User user = userRepository.findByEmail(principal.getName());
        System.out.println("Principal: " + principal.getName());
        user.setUsername(username);
        user.setDateOfBirth(date);

        userRepository.save(user);

        return "redirect:/cabinet";
    }

    @PostMapping("/change-time")
    public String changeAppointmentTime(
            @RequestParam("time") @DateTimeFormat(pattern = "HH:mm") String timeStr,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("sessionId") Long sessionId,
            Principal principal) {

        LocalTime selectedTime = LocalTime.parse(timeStr);
        User user = userRepository.findByEmail(principal.getName());
        System.out.println("Principal: " + principal.getName());

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Сесію не знайдено"));

        if (!session.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Сесію не знайдено або не ваша");
        }

        session.setTime(selectedTime);
        session.setDate(date);

        sessionRepository.save(session);

        return "redirect:/cabinet";
    }

    @GetMapping("/cabinet")
    public String userCabinet(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Session> sessions = sessionService.getUpcomingSessionsForUser(user.getId());

        Map<SessionStatus, String> statusMap = Map.of(
                SessionStatus.PENDING, "Очікує підтвердження",
                SessionStatus.APPROVED, "Підтверджено",
                SessionStatus.CANCELLED, "Скасовано"
        );

        model.addAttribute("user", user);
        model.addAttribute("sessions", sessions);
        model.addAttribute("statusMap", statusMap);

        return "user-cabinet";
    }

    @PostMapping("/cancel-session/{id}")
    public String cancelSession(@PathVariable("id") Long sessionId, Principal principal) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Сеанс не знайдено"));

        String email = principal.getName();

        User user = userRepository.findByEmail(email);
        if (user != null && session.getUser().getId().equals(user.getId())) {
            sessionRepository.delete(session);
            return "redirect:/cabinet";
        }

        Physician physician = physicianRepository.findByEmail(email);
        if (physician != null && session.getPhysician().getId().equals(physician.getId())) {
            sessionRepository.delete(session);
            return "redirect:/cabinet";
        }

        throw new RuntimeException("Ви не маєте права скасовувати цей сеанс");
    }

}
