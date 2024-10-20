package com.devjc.SistemaHotel.sevice; // Asegúrate de que el nombre del paquete sea correcto

import com.devjc.SistemaHotel.entity.Room;
import com.devjc.SistemaHotel.entity.RoomStatus;
import com.devjc.SistemaHotel.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public Iterable<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room create(Room room) {
        // Asegúrate de que la habitación esté disponible antes de crearla
        if (room.isOccupied()) {
            throw new IllegalStateException("Error: La habitación ya está ocupada.");
        }
        room.setStatus(RoomStatus.AVAILABLE); // Asegúrate de que el estado sea 'DISPONIBLE' al crear
        return roomRepository.save(room);
    }

    public Room update(Integer id, Room form) {
        Room roomFromDb = findById(id);
        if (roomFromDb != null) {
            roomFromDb.setNumber(form.getNumber());
            roomFromDb.setStatus(form.getStatus());
            roomFromDb.setPrice(form.getPrice());
            return roomRepository.save(roomFromDb);
        }
        return null; // O puedes lanzar una excepción
    }

    public void delete(Integer id) {
        Room roomFromDb = findById(id);
        if (roomFromDb != null) {
            // Antes de eliminar, verifica si la habitación está ocupada
            if (roomFromDb.isOccupied()) {
                throw new IllegalStateException("Error: No se puede eliminar una habitación ocupada.");
            }
            roomRepository.delete(roomFromDb);
        }
    }

    public String assignRoomToClient(Integer roomId) {
        Room room = findById(roomId);
        if (room == null) {
            return "Error: Habitación no encontrada.";
        }

        if (room.isOccupied()) {
            return "Error: La habitación ya está ocupada.";
        }

        // Asigna la habitación al cliente y cambia su estado a ocupado
        room.setOccupied(true); // Marca la habitación como ocupada
        roomRepository.save(room); // Guarda los cambios
        return "Habitación asignada correctamente.";
    }

    public String freeRoom(Integer roomId) {
        Room room = findById(roomId);
        if (room == null) {
            return "Error: Habitación no encontrada.";
        }

        room.setOccupied(false); // Marca la habitación como libre
        roomRepository.save(room); // Guarda los cambios
        return "Habitación liberada correctamente.";
    }
}