package com.example.transportationapplication.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATIONS", schema = "C##DIPLOMA")
public class BusData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "RESERVATION_DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @Column(name = "BUS_ID")
    private int busId;

    @Column(name = "AVAILABLE_SEATS")
    private int availableSeats;

    @Column(name = "FROM_DESTINATION")
    private String fromDestination;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "TIME")
    private String time;

    @Column(name = "TO_DESTINATION")
    private String toDestination;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busName) {
        this.busId = busName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
