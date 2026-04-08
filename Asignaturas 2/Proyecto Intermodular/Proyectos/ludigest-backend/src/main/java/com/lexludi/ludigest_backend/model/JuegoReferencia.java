package com.lexludi.ludigest_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
 // VALIDACION: No puede ser nulo ya que es nuestra clave de sincronizacion
    @NotNull(message = "El ID de la BGG es obligatorio para la sincronizacion")
    @Column(name = "id_bgg", unique = true)
    private Long idBgg;

 // VALIDACION: El titulo no puede estar vacio ni ser solo espacios
    @NotBlank(message = "El titulo del juego no puede estar vacio")
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 255)
    private String urlImagen;

    @NotNull(message = "Debes especificar el numero minimo de jugadores")
    private Integer minJugadores;

    @NotNull(message = "Debes especificar el numero maximo de jugadores")
    private Integer maxJugadores;

    @NotNull(message = "La duracion estimada es obligatoria")
    private Integer duracionMinutos;
    
    private Double puntuacionBgg;

    // --- NUEVOS CAMPOS APLICADOS (FASE INICIAL)---
    
    // Campo para guardar el numero ideal de jugadores segun la BGG
    @Column(length = 50)
    private String jugadoresRecomendados;

    // Categoria principal del juego (ej: Strategy, Family, etc.)
    @Column(length = 100)
    private String categoria;

    // Dureza o peso del juego (complejidad)
    private Double dureza;
    
 // --- NUEVOS CAMPOS APLICADOS (FASE LUDOTECA) ---

    // Mecanicas principales del juego (ej: Colocacion de trabajadores, Draft, etc.)
    // Opcional, para no chocar con los datos existentes
    @Column(length = 255)
    private String mecanicas;

    // Ano en el que el juego fue lanzado al mercado (sin ene por convencion de codigo)
    // Opcional
    private Integer anoPublicacion;

    // Creador o creadores principales del juego
    // Opcional
    @Column(length = 150)
    private String autor;

    // Sinopsis o descripcion larga del juego. 
    // Usamos columnDefinition = "TEXT" para que MySQL permita textos muy largos sin el limite de 255 caracteres
    // Opcional
    @Column(columnDefinition = "TEXT")
    private String descripcion;
}