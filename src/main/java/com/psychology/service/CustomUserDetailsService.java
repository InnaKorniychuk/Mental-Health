package com.psychology.service;

import com.psychology.model.Physician;
import com.psychology.repository.PhysicianRepository;
import com.psychology.repository.UserRepository;
import com.psychology.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );
        }

        Physician physician = physicianRepository.findByEmail(email);
        if (physician != null) {
            return new org.springframework.security.core.userdetails.User(
                    physician.getEmail(),
                    physician.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + physician.getRole().name()))
            );
        }

        throw new UsernameNotFoundException(
                "User is not found with the email: " + email
        );
    }
}
