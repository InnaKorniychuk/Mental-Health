package com.psychology.service;

import com.psychology.model.Physician;
import com.psychology.repository.PhysicianRepository;
import com.psychology.repository.UserRepository;
import com.psychology.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PhysicianRepository physicianRepository;

    public CustomUserDetailsService(UserRepository userRepository, PhysicianRepository physicianRepository) {
        this.userRepository = userRepository;
        this.physicianRepository=physicianRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().getAuthorities()
            );
        }

        Physician physician = physicianRepository.findByEmail(email);
        if (physician != null) {
            return new org.springframework.security.core.userdetails.User(
                    physician.getEmail(),
                    physician.getPassword(),
                    physician.getRole().getAuthorities()
            );
        }
        throw new UsernameNotFoundException("Користувача з такою електронною адресою не знайдено: " + email);
    }
}
