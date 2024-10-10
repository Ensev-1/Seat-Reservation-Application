package com.example.transportationapplication.controller;

import com.example.transportationapplication.DTO.BookingsDTO;
import com.example.transportationapplication.DTO.ReservationDTO;
import com.example.transportationapplication.helper.ObjectCreationHelper;
import com.example.transportationapplication.model.*;
import com.example.transportationapplication.repository.BookingsRepository;
import com.example.transportationapplication.repository.BusDataRepository;
import com.example.transportationapplication.repository.UserRepository;
import com.example.transportationapplication.service.CityService;
import com.example.transportationapplication.service.CityServiceImpl;
import com.example.transportationapplication.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private DefaultUserService userService;


    public DashboardController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BusDataRepository busDataRepository;

    @Autowired
    private CityServiceImpl cityService;

    @ModelAttribute("reservation")
    public ReservationDTO reservationDTO() {
        return new ReservationDTO();
    }

    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        List<String> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);
        return "dashboard";
    }

    @PostMapping
    public String filterBusData(@ModelAttribute("reservation") ReservationDTO reservationDTO, Model model) {
        List<BusData> busData = busDataRepository.findByToFromAndDate(reservationDTO.getTo(), reservationDTO.getFrom(), reservationDTO.getReservationDate());

        if (busData.isEmpty()) {
            busData = null;
        }

        String user = returnUsername();

        List<String> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);

        model.addAttribute("userDetails", user);
        model.addAttribute("busData", busData);
        model.addAttribute("reservation", reservationDTO);

        return "dashboard";
    }

    @GetMapping("/book/{id}")
    public String bookPage(@PathVariable int id, Model model) {
        Optional<BusData> busData = busDataRepository.findById(id);

        if (busData.isPresent()) {
            BusData bus = busData.get();
            BookingsDTO bks = ObjectCreationHelper.createBookingsDTO(bus);
            bks.setAvailableSeats(bus.getAvailableSeats());
            bks.setBusId(bus.getBusId());
            bks.setReservationDate(bus.getReservationDate());

            Optional<BusData> reservation = busDataRepository.findById(bus.getId());
            if (reservation.isPresent()) {
                bks.setReservationId(reservation.get().getId()); // Set the reservation ID in the BookingsDTO
            }

            String user = returnUsername();
            model.addAttribute("userDetails", user);
            model.addAttribute("record", bks);
        } else {
            model.addAttribute("error", "Bus data not found.");
            return "errorPage";
        }

        return "book";
    }


    @PostMapping("/booking")
    public String finalBooking(@ModelAttribute("record") BookingsDTO bookingDTO, Model model) {
        int reservationAvailableSeats = bookingDTO.getAvailableSeats();

        if (bookingDTO.getNoOfPersons() > reservationAvailableSeats) {
            model.addAttribute("error", "Not enough seats available.");
            return "book";
        }

        // Update available seats using busId
        int newAvailableSeats = reservationAvailableSeats - bookingDTO.getNoOfPersons();
        busDataRepository.updateAvailableSeatsByBusId(bookingDTO.getBusId(),bookingDTO.getReservationId(), newAvailableSeats);

        // Proceed with booking
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        userService.updateBookings(bookingDTO, user);

        return "redirect:/myBooking";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<String> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);
        return "dashboard";
    }




    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        return users.getName();
    }

}
