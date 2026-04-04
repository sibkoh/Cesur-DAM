package com.lexludi.ludigest_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad para gestionar el catalogo teorico de juegos (la ficha del juego)
@Entity
@Table(name = "juegos_referencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuegoReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guardamos el ID de BoardGameGeek para futuras sincronizaciones
    // BLINDAJE: unique = true asegura que MySQL rechazara cualquier intento de duplicado
    @Column(name = "id_bgg", unique = true)
    private Long idBgg;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 255)
    private String urlImagen;

    private Integer minJugadores;
    private Integer maxJugadores;
    private Integer duracionMinutos;
    private Double puntuacionBgg;

    // --- NUEVOS CAMPOS APLICADOS ---
    
    // Campo para guardar el numero ideal de jugadores segun la BGG
    @Column(length = 50)
    private String jugadoresRecomendados;

    // Categoria principal del juego (ej: Strategy, Family, etc.)
    @Column(length = 100)
    private String categoria;

    // Dureza o peso del juego (complejidad)
    private Double dureza;
}