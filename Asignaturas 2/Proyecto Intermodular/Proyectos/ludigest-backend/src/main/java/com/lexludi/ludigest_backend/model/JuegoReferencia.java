package com.lexludi.ludigest_backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Ficha tecnica pura con los datos sacados de la BGG
@Entity
@Table(name = "juegos_referencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuegoReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    // Guardamos el ID de BoardGameGeek para futuras sincronizaciones
    @Column(name = "id_bgg")
    private Long idBgg;

    // Nota media en la BGG. Usamos Double para soportar decimales (ej: 8.4)
    @Column(name = "puntuacion_bgg")
    private Double puntuacionBgg;

    @Column(nullable = false)
    private Integer minJugadores;

    @Column(nullable = false)
    private Integer maxJugadores;

    // Cuanto dura una partida en minutos segun la caja
    private Integer duracionMinutos;

    // Enlace a la portada del juego para el futuro frontend
    @Column(length = 500)
    private String urlImagen;
}