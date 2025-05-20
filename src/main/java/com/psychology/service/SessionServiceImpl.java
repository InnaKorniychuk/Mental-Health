package com.psychology.service;

import com.psychology.model.Physician;
import com.psychology.model.Session;
import com.psychology.model.SessionStatus;
import com.psychology.repository.PhysicianRepository;
import com.psychology.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PhysicianRepository physicianRepository;

    @Override
    public List<Session> getPendingSessions() {
        return sessionRepository.findByStatus(SessionStatus.PENDING);
    }
    @Override
    public void approveSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Сесія не знайдена"));
        session.setStatus(SessionStatus.APPROVED);
        sessionRepository.save(session);
    }

//    public void cancelSession(Long sessionId) {
//        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Сесія не знайдена"));
//        session.setStatus(SessionStatus.CANCELLED);
//        sessionRepository.save(session);
//    }

    @Override
    public List<Session> getUpcomingSessionsForUser(Long userId) {
        return sessionRepository.findByUserId(userId).stream()
                .filter(s -> s.getDate().isAfter(LocalDate.now()) ||
                        (s.getDate().isEqual(LocalDate.now()) && s.getTime().isAfter(LocalTime.now())))
                .collect(Collectors.toList());
    }
    @Override
    public List<Session> getSessionsForPhysician(String email) {
        // Знайти психолога за email
        Physician physician = physicianRepository.findByEmail(email);
        if (physician != null) {
            // Повернути всі сесії цього психолога
            return sessionRepository.findByPhysician(physician);
        }
        return new ArrayList<>();
    }

    @Override
    public void cancelSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}