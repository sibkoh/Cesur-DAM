package com.lexludi.ludigest_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// Entidad que representa a los miembros de la asociacion
@Entity
@Table(name = "socios")
@Getter
@Setter
@NoArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 100)
    private String correoElectronico;

    // Controlamos cuando se unio para temas de antiguedad
    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    // Indica si el socio esta al corriente de pago y puede sacar juegos
    @Column(nullable = false)
    private boolean estadoActivo;
}