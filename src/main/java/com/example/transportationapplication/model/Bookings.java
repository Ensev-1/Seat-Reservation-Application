package com.example.transportationapplication.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKINGS", schema = "C##DIPLOMA")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "BUS_ID")
    private int busId;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "RESERVATION_DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @Column(name = "FROM_DESTINATION")
    private String fromDestination;

    @Column(name = "NO_OF_PERSONS")
    private int noOfPersons;

    @Column(name = "TIME")
    private String time;

    @Column(name = "TO_DESTINATION")
    private String toDestination;

    @Column(name = "TOTAL_CALCULATED")
    private Double totalCalculated;

    @Column(name = "TRIP_STATUS")
    private boolean tripStatus;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "RESERVATION_ID")
    private int reservationId;


    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public Double getTotalCalculated() {
        return totalCalculated;
    }

    public void setTotalCalculated(Double totalCalculated) {
        this.totalCalculated = totalCalculated;
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

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(boolean tripStatus) {
        this.tripStatus = tripStatus;
    }

}
