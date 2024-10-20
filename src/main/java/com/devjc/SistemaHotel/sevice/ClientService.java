package com.devjc.SistemaHotel.sevice;

import com.devjc.SistemaHotel.entity.Client;
import com.devjc.SistemaHotel.entity.Room;
import com.devjc.SistemaHotel.entity.RoomStatus;
import com.devjc.SistemaHotel.repository.ClientRepository;
import com.devjc.SistemaHotel.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@CrossOrigin
@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    public Client create(Client client) {
        if (client.getRoom() != null && client.getRoom().getId() != null) {
            Room room = roomRepository.findById(client.getRoom().getId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            if (room.getStatus() != RoomStatus.AVAILABLE) {
                throw new IllegalStateException("La habitación no está disponible");
            }

            client.setRoom(room);
            room.setStatus(RoomStatus.OCCUPIED);
            roomRepository.save(room);
        } else {
            throw new IllegalArgumentException("Room ID must not be null");
        }

        client.setCreateAt(LocalDateTime.now());
        return clientRepository.save(client);
    }

    public Client update(Long id, Client form) {
        Client clientFromDb = findById(id);
        if (clientFromDb != null) {
            clientFromDb.setName(form.getName());
            clientFromDb.setEmail(form.getEmail());
            clientFromDb.setNumber(form.getNumber());
            clientFromDb.setCedula(form.getCedula()); // Actualizar cedula
            clientFromDb.setCiudadD(form.getCiudadD()); // Actualizar ciudadD

            // Actualizar la habitación solo si se ha proporcionado una válida
            if (form.getRoom() != null && form.getRoom().getId() != null) {
                Room room = roomRepository.findById(form.getRoom().getId())
                        .orElseThrow(() -> new RuntimeException("Room not found"));
                clientFromDb.setRoom(room); // Asignamos la habitación existente
            }

            return clientRepository.save(clientFromDb);
        }
        return null; // Manejo de error si no se encuentra el cliente
    }

    public void delete(Long id) {
        Client clientFromDb = findById(id);
        if (clientFromDb != null) {
            if (clientFromDb.getRoom() != null) {
                Room room = clientFromDb.getRoom();
                room.setStatus(RoomStatus.AVAILABLE);
                roomRepository.save(room);
            }
            clientRepository.delete(clientFromDb);
        }
    }
}
