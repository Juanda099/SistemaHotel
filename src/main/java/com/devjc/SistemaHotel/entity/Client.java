package com.devjc.SistemaHotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private String email;
    private String cedula;
    private String ciudadD;

    @OneToOne
    @JsonIgnore
    private Room room;

    private LocalDateTime createAt; // Campo para la fecha de creación

    // Constructor por defecto
    public Client() {
    }

    // Constructor con parámetros
    public Client(String name, String number, String email, Room room, String cedula, String ciudadD) {
        this.name = name;
        this.cedula = cedula;
        this.ciudadD = ciudadD;
        this.number = number;
        this.email = email;
        this.room = room;
        this.createAt = LocalDateTime.now(); // Establecer fecha de creación al instante de creación
    }

    // Método para obtener el ID de la habitación
    public Integer getRoomId() {
        return room != null ? room.getId() : null; // Devuelve el ID de la habitación o null si no hay habitación
    }

    // Método para establecer el ID de la habitación
    public void setRoomId(Integer roomId) {
        this.room = new Room(roomId); // Crea un nuevo objeto Room solo con el ID
    }
}
