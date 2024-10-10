package com.example.transportationapplication.repository;

import com.example.transportationapplication.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

    boolean existsByEmail(String email);

}
