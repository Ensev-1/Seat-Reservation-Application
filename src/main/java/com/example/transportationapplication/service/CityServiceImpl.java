package com.example.transportationapplication.service;

import com.example.transportationapplication.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityRepository cityRepository;


    public List<String> getAllCities() {
        return cityRepository.findAllCityNames();
    }

}






