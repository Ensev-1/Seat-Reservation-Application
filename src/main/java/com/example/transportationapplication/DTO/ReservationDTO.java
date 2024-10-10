package com.example.transportationapplication.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ReservationDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;
    private String to;
    private String from;


    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


}
