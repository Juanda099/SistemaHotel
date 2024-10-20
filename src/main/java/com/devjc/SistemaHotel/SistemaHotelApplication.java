package com.devjc.SistemaHotel;

import com.devjc.SistemaHotel.entity.Client;
import com.devjc.SistemaHotel.entity.Reservation;
import com.devjc.SistemaHotel.entity.Room;
import com.devjc.SistemaHotel.entity.RoomStatus;
import com.devjc.SistemaHotel.repository.ClientRepository;
import com.devjc.SistemaHotel.repository.RoomRepository;
import com.devjc.SistemaHotel.repository.ReservationRepository;  // Importa el ReservationRepository
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SistemaHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaHotelApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ClientRepository clientRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
		return args -> {
			initializeRooms(roomRepository);
			initializeClients(clientRepository, roomRepository);
			initializeReservations(reservationRepository, clientRepository, roomRepository);
		};
	}

	private void initializeRooms(RoomRepository roomRepository) {
		List<Room> rooms = Arrays.asList(
				new Room("101", RoomStatus.AVAILABLE, 100.0),
				new Room("102", RoomStatus.AVAILABLE, 120.0),
				new Room("103", RoomStatus.OCCUPIED, 150.0),
				new Room("104", RoomStatus.RESERVED, 80.0),
				new Room("105", RoomStatus.AVAILABLE, 110.0)
		);
		roomRepository.saveAll(rooms);  // Guarda las habitaciones
	}

	private void initializeClients(ClientRepository clientRepository, RoomRepository roomRepository) {
		List<Room> rooms = roomRepository.findAll();

		if (rooms.size() < 5) {
			throw new RuntimeException("No hay suficientes habitaciones para asignar a los clientes.");
		}

		List<Client> clients = Arrays.asList(
				new Client("Juan", "315213482", "juan@gmail.com", rooms.get(0), "12345678", "Bogotá"),
				new Client("Mario", "315213482", "mario@gmail.com", rooms.get(1), "87654321", "Medellín"),
				new Client("Guillo", "315213482", "guillo@gmail.com", rooms.get(2), "11223344", "Cali"),
				new Client("María", "315213482", "maria@gmail.com", rooms.get(3), "44332211", "Cartagena"),
				new Client("Karla", "315213482", "karla@gmail.com", rooms.get(4), "55667788", "Barranquilla")
		);
		clientRepository.saveAll(clients);  // Guarda los clientes
	}

	private void initializeReservations(ReservationRepository reservationRepository, ClientRepository clientRepository, RoomRepository roomRepository) {
		List<Client> clients = (List<Client>) clientRepository.findAll();
		List<Room> rooms = roomRepository.findAll();

		if (clients.isEmpty() || rooms.isEmpty()) {
			throw new RuntimeException("No hay suficientes clientes o habitaciones para crear reservas.");
		}

		List<Reservation> reservations = Arrays.asList(
				new Reservation(clients.get(0), rooms.get(0), LocalDateTime.now(), LocalDateTime.now().plusDays(2)), // Juan reserva
				new Reservation(clients.get(1), rooms.get(1), LocalDateTime.now(), LocalDateTime.now().plusDays(1)), // Mario reserva
				new Reservation(clients.get(2), rooms.get(2), LocalDateTime.now(), LocalDateTime.now().plusDays(3))  // Guillo reserva
				// Agrega más reservas según sea necesario
		);
		reservationRepository.saveAll(reservations);  // Guarda las reservas
	}
}