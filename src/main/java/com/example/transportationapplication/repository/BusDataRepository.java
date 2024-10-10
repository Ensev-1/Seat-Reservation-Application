package com.example.transportationapplication.repository;

import com.example.transportationapplication.model.BusData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface BusDataRepository extends JpaRepository<BusData, Integer> {

    @Query(value = "select * from Reservations  where reservations.to_destination =:to and reservations.from_destination =:from and reservations.reservation_date =:date  order By reservations.reservation_date desc ", nativeQuery = true)
    List<BusData> findByToFromAndDate(String to, String from, Date date);

    @Transactional
    @Modifying
    @Query(value ="UPDATE Reservations r SET r.available_Seats = :availableSeats WHERE r.bus_id = :busId AND r.id = :reservationId", nativeQuery = true)
    void updateAvailableSeatsByBusId(@Param("busId") int busId,@Param("reservationId") int reservationId,@Param("availableSeats") int availableSeats);

}
