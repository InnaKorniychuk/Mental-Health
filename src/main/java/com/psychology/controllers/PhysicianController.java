package com.psychology.controllers;

import com.psychology.model.Physician;
import com.psychology.repository.PhysicianRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PhysicianController {

    private final PhysicianRepository physicianRepository;

    public PhysicianController(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }


    @GetMapping("/physicians")
    public String getAllPhysicians(Model model) {
        List<Physician> physicians = physicianRepository.findAll();
        model.addAttribute("physicians", physicians);
        return "psychologists/physicians";
    }
    @GetMapping("/payment")
    public String getPaymentPage() {
        return "psychologists/payment";
    }

}

