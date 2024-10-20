package com.devjc.SistemaHotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Client client;

    @NotNull
    @ManyToOne
    private Room room;

    private Double pricePerNight; // Campo para el precio por noche

    @NotNull
    private LocalDateTime startDate; // Fecha de inicio

    @NotNull
    private LocalDateTime endDate;   // Fecha de finalización

    private Double totalPrice;       // Precio total

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // Estado de la reservación

    public Reservation() {
        this.status = ReservationStatus.PENDING; // Estado inicial como pendiente
    }

    public Reservation(Client client, Room room, LocalDateTime startDate, LocalDateTime endDate) {
        this.client = client;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.PENDING; // Estado inicial como pendiente
        calculateTotalPrice(); // Calcula el precio total al crear la reserva
    }

    public void calculateTotalPrice() {
        if (room != null) {
            if (room.getPricePerNight() == null) {
                throw new IllegalStateException("El precio por noche no puede ser nulo.");
            }
            if (startDate != null && endDate != null) {
                if (startDate.isBefore(endDate)) {
                    long duration = java.time.Duration.between(startDate, endDate).toDays();
                    totalPrice = duration * room.getPricePerNight();
                } else {
                    throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de finalización.");
                }
            }
        }
    }

    @PrePersist
    @PreUpdate
    private void calculateTotalPriceBeforeSave() {
        calculateTotalPrice();
    }
}
