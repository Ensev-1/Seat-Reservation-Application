package com.example.transportationapplication.controller;

import com.example.transportationapplication.DTO.UserLoginDTO;
import com.example.transportationapplication.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private DefaultUserService userService;


    @ModelAttribute("user")
    public UserLoginDTO userLoginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public void loginUser(@ModelAttribute("user") UserLoginDTO userLoginDTO) {
        userService.loadUserByUsername(userLoginDTO.getUsername());
    }

}
