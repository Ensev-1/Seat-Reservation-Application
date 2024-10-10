package com.example.transportationapplication.model;

import javax.persistence.*;

@Entity
@Table(name = "BUS", schema = "C##DIPLOMA")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BUS_ID")
    private int busId;

    @Column(name = "COMPANY_ID")
    private int companyId;

    @Column(name = "SEATS")
    private int seats;

    @Column(name = "BAG_CAPACITY")
    private int bagCapacity;

    @Column(name = "TERMINAL_ID")
    private int terminalId;

    @Column(name = "STATUS")
    private char status;

    @Column(name = "BUS_NAME")
    private String busName;


    public int getBusId() {
        return busId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getSeats() {
        return seats;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public char getStatus() {
        return status;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setBagCapacity(int bagCapacity) {
        this.bagCapacity = bagCapacity;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}