package com.lexludi.ludigest_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Ficha tecnica e inmutable de un juego (independiente de cuantas copias tengamos)
@Entity
@Table(name = "juegos_referencia")
@Getter
@Setter
@NoArgsConstructor
public class JuegoReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 100)
    private String editorial;

    // Identificador de la BoardGameGeek por si luego integramos la API
    @Column(name = "bgg_id")
    private Integer bggId;

    @Column(nullable = false)
    private Integer minJugadores;

    @Column(nullable = false)
    private Integer maxJugadores;
}