package com.example.transportationapplication.controller;

import com.example.transportationapplication.model.User;
import com.example.transportationapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terminals")
public class TerminalsController {

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);

        return "terminals";
    }

    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());

        return users.getName();
    }

}
