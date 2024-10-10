package com.example.transportationapplication.helper;

import com.example.transportationapplication.DTO.BookingsDTO;
import com.example.transportationapplication.model.Bookings;
import com.example.transportationapplication.model.BusData;

public class ObjectCreationHelper {

    public static BookingsDTO createBookingsDTO(BusData busdata) {
        BookingsDTO bks = new BookingsDTO();

        bks.setBusId(busdata.getId());
        bks.setReservationDate(busdata.getReservationDate());
        bks.setFromDestination(busdata.getFromDestination());
        bks.setToDestination(busdata.getToDestination());
        bks.setPrice(busdata.getPrice());
        bks.setNoOfPersons(0);
        bks.setTime(busdata.getTime());
        bks.setTotalCalculated(0.0);

        return bks;
    }

    public static BookingsDTO createBookingsDTO(Bookings busData) {
        BookingsDTO bks = new BookingsDTO();

        bks.setId(busData.getId());
        bks.setBusId(busData.getBusId());
        bks.setReservationDate(busData.getReservationDate());
        bks.setFromDestination(busData.getFromDestination());
        bks.setToDestination(busData.getToDestination());
        bks.setNoOfPersons(busData.getNoOfPersons());
        bks.setTime(busData.getTime());
        bks.setTotalCalculated(busData.getTotalCalculated());

        if (busData.isTripStatus() == true) {
            bks.setTripStatus("Active");
        } else {
            bks.setTripStatus("Canceled");
        }

        return bks;
    }

}
