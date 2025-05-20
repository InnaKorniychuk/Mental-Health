package com.psychology.repository;

import com.psychology.model.Physician;
import com.psychology.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {
    Physician findByEmail(String email);
}
