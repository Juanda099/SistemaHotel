package com.devjc.SistemaHotel.repository;

import com.devjc.SistemaHotel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
