package com.devjc.SistemaHotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String number; // Número de habitación

    @NonNull
    @Enumerated(EnumType.STRING)
    private RoomStatus status; // Estado de la habitación (disponible, ocupada, etc.)

    @NonNull
    private Double price; // Precio de la habitación por noche

    // Constructor para inicializar con todos los parámetros
    public Room(String number, RoomStatus status, Double price) {
        this.number = number;
        this.status = status;
        this.price = price;
    }

    public Room(Integer roomId) {
        this.id = roomId; // Se debe usar roomId en lugar de id
    }

    // Método personalizado para obtener el precio por noche
    public Double getPricePerNight() {
        return this.price;
    }

    // Método para verificar si la habitación está ocupada
    public boolean isOccupied() {
        return this.status == RoomStatus.OCCUPIED; // Suponiendo que 'OCCUPIED' es el estado que indica que la habitación está ocupada
    }

    // Método para establecer la habitación como ocupada
    public void setOccupied(boolean occupied) {
        this.status = occupied ? RoomStatus.OCCUPIED : RoomStatus.AVAILABLE; // Asigna el estado correspondiente
    }
}
