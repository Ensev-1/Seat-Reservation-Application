package com.example.transportationapplication.controller;

import com.example.transportationapplication.DTO.UserRegisteredDTO;
import com.example.transportationapplication.service.DefaultUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final DefaultUserService userService;


    public RegistrationController(DefaultUserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto() {
        return new UserRegisteredDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegisteredDTO registrationDto, Model model) {
        try {
            userService.save(registrationDto);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            // User already exists
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
