package com.lexludi.ludigest_backend.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// La caja fisica concreta que un socio coge de la estanteria
@Entity
@Table(name = "ejemplares")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El numero de la pegatina que pegamos en la caja (codigo local)
    @Column(nullable = false, unique = true, length = 50)
    private String codigoLocal;

    // Lo vinculamos con su ficha tecnica (muchas cajas pueden ser el mismo juego)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_referencia_id")
    private JuegoReferencia juegoReferencia;

    // En que armario o estanteria fisica del local esta ubicado
    @Column(length = 50)
    private String balda;

    // IMPORTANTE: Usamos este campo para calcular la cuarentena de 30 dias de LexLudi
    // Ningun juego nuevo podra prestarse hasta que pasen 30 dias desde esta fecha
    @Column(nullable = false)
    private LocalDate fechaAdquisicion;

    // Ciclo de vida de la caja: "DISPONIBLE", "PRESTADO", "RESERVADO", "MANTENIMIENTO"
    @Column(nullable = false, length = 20)
    private String estado = "DISPONIBLE";

    // Avisos como "falta un meeple azul" o "caja rajada en la esquina"
    @Column(length = 500)
    private String anotaciones;
}