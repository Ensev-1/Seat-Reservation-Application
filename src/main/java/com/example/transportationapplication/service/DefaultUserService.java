package com.example.transportationapplication.service;

import com.example.transportationapplication.DTO.BookingsDTO;
import com.example.transportationapplication.DTO.UserRegisteredDTO;
import com.example.transportationapplication.model.Bookings;
import com.example.transportationapplication.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService{

	void save(UserRegisteredDTO userRegisteredDTO);

	Bookings updateBookings(BookingsDTO bookingDTO,UserDetails user);
	
	void sendEmail(BookingsDTO bookingDTO, User users, String nameGenerator);

}
