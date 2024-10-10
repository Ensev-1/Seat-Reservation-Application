package com.example.transportationapplication.controller;

import com.example.transportationapplication.DTO.FeedbackDTO;
import com.example.transportationapplication.model.Feedback;
import com.example.transportationapplication.model.User;
import com.example.transportationapplication.repository.FeedbackRepo;
import com.example.transportationapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackRepo feedbackRepo;


    @GetMapping
    public String feedbackForm(Model model) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setEmailId(returnUsername().get("email"));
        dto.setName(returnUsername().get("name"));
        model.addAttribute("userDetails", returnUsername().get("name"));
        model.addAttribute("feedback", dto);
        return "feedback";
    }

    @PostMapping
    public String saveFeedback(@ModelAttribute("feedback") FeedbackDTO feedbackDTO, Model model) {
        Map<String, String> map = returnUsername();

        int rating = feedbackDTO.getRating();

        if (rating < 0 || rating > 10) {
            model.addAttribute("error", "Rating must be a number between 0 and 10.");
            return "feedback";
        }

        String feedbackComments = feedbackDTO.getComments().trim();
        if (feedbackComments.isEmpty()) {
            model.addAttribute("error", "Feedback cannot be empty or just whitespace.");
            return "feedback";
        }

        boolean feedbackAlreadyExists = feedbackRepo.existsByEmail(map.get("email"));
        if (feedbackAlreadyExists) {
            model.addAttribute("error", "You have already submitted a feedback.");
            return "feedback";
        }

        Feedback feedback = new Feedback();

        String sanitizedComments = feedbackDTO.getComments().replaceAll("[<>]", "");
        feedback.setComments(sanitizedComments);

        feedback.setRating(rating);
        feedback.setName(map.get("name"));
        feedback.setEmail(map.get("email"));

        feedbackRepo.save(feedback);

        return "redirect:/feedback?success";
    }


    private Map<String, String> returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        Map<String, String> map = new HashMap<>();
        map.put("email", users.getEmail());
        map.put("name", users.getName());
        return map;
    }
}
