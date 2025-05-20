package com.psychology.repository;

;
import com.psychology.model.Physician;
import com.psychology.model.Session;
import com.psychology.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserId(Long userId);
    List<Session> findByStatus(SessionStatus status);
    List<Session> findByPhysician(Physician physician);
//    @Query("SELECT s FROM Session s JOIN FETCH s.user JOIN FETCH s.physician")
//    List<Session> findAllWithUsersAndPhysicians();
}