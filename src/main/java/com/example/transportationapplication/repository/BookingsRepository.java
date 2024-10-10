package com.example.transportationapplication.repository;

import com.example.transportationapplication.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

    List<Bookings> findByUserId(int userId);

}
