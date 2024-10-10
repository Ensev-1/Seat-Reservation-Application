package com.example.transportationapplication.model;

import javax.persistence.*;

@Entity
@Table(name = "CITIES", schema = "C##DIPLOMA")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CITY_ID")
    private int id;

    @Column(name = "CITY_NAME")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

