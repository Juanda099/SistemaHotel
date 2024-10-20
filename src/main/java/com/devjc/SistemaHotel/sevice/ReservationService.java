package com.devjc.SistemaHotel.sevice;

import com.devjc.SistemaHotel.entity.Reservation;
import com.devjc.SistemaHotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Crear una nueva reserva
    public Reservation createReservation(Reservation reservation) {
        // Validar que el precio por noche no sea nulo
        if (reservation.getPricePerNight() == null) {
            throw new IllegalStateException("El precio por noche no puede ser nulo.");
        }

        // Calcular el precio total
        reservation.calculateTotalPrice(); // Asegúrate de que este método esté bien implementado
        return reservationRepository.save(reservation); // Guarda la nueva reserva
    }

    // Obtener todas las reservas
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll(); // Devuelve todas las reservas
    }

    // Obtener una reserva por ID
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id); // Devuelve una reserva por ID
    }

    // Actualizar una reserva
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        // Verificar si la reserva existe
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Actualiza los campos según reservationDetails
        reservation.setClient(reservationDetails.getClient());
        reservation.setRoom(reservationDetails.getRoom());
        reservation.setStartDate(reservationDetails.getStartDate());
        reservation.setEndDate(reservationDetails.getEndDate());

        // Validar que el precio por noche no sea nulo
        if (reservationDetails.getPricePerNight() == null) {
            throw new IllegalStateException("El precio por noche no puede ser nulo.");
        }
        reservation.setPricePerNight(reservationDetails.getPricePerNight());

        // Recalcular el precio total
        reservation.calculateTotalPrice();

        return reservationRepository.save(reservation); // Guarda la reserva actualizada
    }

    // Eliminar una reserva
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id); // Elimina la reserva por ID
    }
}
