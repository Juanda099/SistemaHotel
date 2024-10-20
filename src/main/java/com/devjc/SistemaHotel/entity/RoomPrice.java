package com.devjc.SistemaHotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class RoomPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double basePrice; // Precio base por noche

    // Otros atributos para el cálculo del precio (por ejemplo, temporada, descuentos, etc.)
    // Aquí podrías agregar lógica adicional si el precio varía

    // Método para obtener el precio final por noche
    public Double getFinalPrice() {
        // Aquí podrías agregar lógica adicional para modificar el precio, como descuentos o tarifas
        return this.basePrice;
    }
}
