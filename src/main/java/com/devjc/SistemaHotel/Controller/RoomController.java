package com.devjc.SistemaHotel.Controller;

import com.devjc.SistemaHotel.entity.Room;
import com.devjc.SistemaHotel.sevice.RoomService; // Asegúrate de que la ruta sea correcta
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
@CrossOrigin // Para permitir el acceso desde otros orígenes
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public Iterable<Room> list() {
        return roomService.findAll();
    }

    @GetMapping("{id}")
    public Room get(@PathVariable Integer id) {
        return roomService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Room create(@RequestBody Room room) {
        // Verificar si la habitación está ocupada antes de crear
        if (room.isOccupied()) {
            throw new IllegalStateException("Error: La habitación ya está ocupada.");
        }
        return roomService.create(room);
    }

    @PutMapping("{id}")
    public Room update(@PathVariable Integer id, @RequestBody Room form) {
        return roomService.update(id, form);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        roomService.delete(id);
    }

    @PostMapping("/{roomId}/assign")
    public String assignRoomToClient(@PathVariable Integer roomId) {
        return roomService.assignRoomToClient(roomId);
    }

    @PostMapping("/{roomId}/free")
    public String freeRoom(@PathVariable Integer roomId) {
        return roomService.freeRoom(roomId);
    }
}
