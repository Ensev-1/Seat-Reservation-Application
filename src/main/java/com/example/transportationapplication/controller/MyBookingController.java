package com.example.transportationapplication.controller;

import com.example.transportationapplication.DTO.BookingsDTO;
import com.example.transportationapplication.helper.ObjectCreationHelper;
import com.example.transportationapplication.model.Bookings;
import com.example.transportationapplication.model.User;
import com.example.transportationapplication.repository.BookingsRepository;
import com.example.transportationapplication.repository.UserRepository;
import com.example.transportationapplication.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/myBooking")
public class MyBookingController {

    private DefaultUserService userService;

    public MyBookingController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserRepository userRepository;


    @ModelAttribute("bookings")
    public BookingsDTO bookingDto() {
        return new BookingsDTO();
    }

    @GetMapping
    public String login(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails users = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(users.getUsername());
        List<BookingsDTO> bks = new ArrayList<BookingsDTO>();
        List<Bookings> bs = bookingsRepository.findByUserId(user.getId());

        for (Bookings bookings : bs) {
            BookingsDTO bk = ObjectCreationHelper.createBookingsDTO(bookings);
            bks.add(bk);
        }

        model.addAttribute("userDetails", user.getName());
        Collections.reverse(bks);
        model.addAttribute("bookings", bks);

        return "myBookings";
    }

    @GetMapping("/generate/{id}")
    public String bookPage(@PathVariable int id, Model model) {
        Optional<Bookings> busData = bookingsRepository.findById(id);
        Optional<User> users = userRepository.findById(busData.get().getUserId());

        String user = users.get().getName();
        BookingsDTO bks = ObjectCreationHelper.createBookingsDTO(busData.get());
        userService.sendEmail(bks, users.get(), busData.get().getFileName());
        model.addAttribute("userDetails", user);
        List<Bookings> bs = bookingsRepository.findByUserId(users.get().getId());
        Collections.reverse(bs);
        model.addAttribute("bookings", bs);

        return "redirect:/myBooking?success";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable int id, Model model) {
        Optional<Bookings> busData = bookingsRepository.findById(id);

        if (busData.get().isTripStatus() == false) {
            setData(busData.get(), model);

            return "redirect:/myBooking?alreadyCancel";
        } else {
            setData(busData.get(), model);
            busData.get().setTripStatus(false);
            bookingsRepository.save(busData.get());

            return "redirect:/myBooking?successCancel";
        }

    }

    private void setData(Bookings busData, Model model) {
        Optional<User> users = userRepository.findById(busData.getUserId());
        List<Bookings> bs = bookingsRepository.findByUserId(users.get().getId());
        Collections.reverse(bs);
        model.addAttribute("bookings", bs);
    }
}
