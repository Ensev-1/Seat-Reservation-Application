package com.example.transportationapplication.controller;

import com.example.transportationapplication.model.Bus;
import com.example.transportationapplication.model.BusData;
import com.example.transportationapplication.model.User;
import com.example.transportationapplication.repository.BusDataRepository;
import com.example.transportationapplication.repository.BusRepository;
import com.example.transportationapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/adminScreen")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BusDataRepository busDataRepository;

    @Autowired
    BusRepository busRepository;


    @ModelAttribute("busDetails")
    public BusData busData() {
        return new BusData();
    }

    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        return "adminScreen";
    }

    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        return users.getName();
    }

    @PostMapping
    public String saveBusData(@ModelAttribute("busDetails") BusData busData, Model model) {
        String user = this.returnUsername();
        model.addAttribute("userDetails", user);

        int busId = busData.getBusId();
        Bus bus = busRepository.findById(busId).orElse(null);

        if (bus != null) {
            busData.setAvailableSeats(bus.getSeats());
        } else {
            model.addAttribute("error", "Bus with ID " + busId + " not found.");
            return "adminScreen";
        }

        busDataRepository.save(busData);

        // Clear the form after saving
        model.addAttribute("busDetails", new BusData());

        return "redirect:/adminScreen?success";
    }


    @GetMapping("/allRecords")
    public String getAllRecords(Model model) {
        List<BusData> data = busDataRepository.findAll();
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        model.addAttribute("data", data);
        return "allRecords";
    }

    @GetMapping("/delete/{id}")
    public String getDataAfterDelete(@PathVariable int id, Model model) {
        busDataRepository.deleteById(id);
        List<BusData> data = busDataRepository.findAll();
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        model.addAttribute("data", data);
        return "redirect:/adminScreen/allRecords?success";
    }

}
