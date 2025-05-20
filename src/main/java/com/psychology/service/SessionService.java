package com.psychology.service;


import com.psychology.model.Session;
import java.util.List;

public interface SessionService {
    List<Session> getUpcomingSessionsForUser(Long userId);
    void cancelSession(Long sessionId);
    void approveSession(Long sessionId);
    List<Session> getPendingSessions();
    public List<Session> getSessionsForPhysician(String email);
}