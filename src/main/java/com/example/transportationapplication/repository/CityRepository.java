package com.example.transportationapplication.repository;

import com.example.transportationapplication.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "SELECT c.city_Name FROM Cities c", nativeQuery = true)
    List<String> findAllCityNames();

}


