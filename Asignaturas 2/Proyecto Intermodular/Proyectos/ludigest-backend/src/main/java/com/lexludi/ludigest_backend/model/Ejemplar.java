package com.lexludi.ludigest_backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad para gestionar cada caja fisica real que tenemos en la ludoteca
@Entity
@Table(name = "ejemplares")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Codigo interno que le ponemos con una pegatina a la caja (ej: CAT-001)
    @Column(nullable = false, unique = true, length = 50)
    private String codigoLocal;

    // Relacion ManyToOne: Muchos ejemplares fisicos pueden pertenecer a una misma ficha teorica
    @ManyToOne
    @JoinColumn(name = "juego_referencia_id", nullable = false)
    private JuegoReferencia juegoReferencia;

    // Lugar fisico donde se guarda en el local
    @Column(length = 50)
    private String balda;

    // Fecha en la que compramos o recibimos el juego (vital para la cuarentena)
    @Column(nullable = false)
    private LocalDate fechaAdquisicion;

    // Estado actual del juego: DISPONIBLE, PRESTADO, RESERVADO, MANTENIMIENTO
    @Column(nullable = false, length = 20)
    private String estado = "DISPONIBLE";

    // Notas sobre componentes perdidos, estado de la caja, etc.
    @Column(length = 500)
    private String anotaciones;

    // --- NUEVO CAMPO APLICADO ---
    
    // Indicador de si la caja fisica esta en el mercadillo de segunda mano
    // Por defecto es false para evitar que se pongan a la venta recien comprados
    @Column(nullable = false)
    private Boolean enVenta = false;
    
 // --- CAMPO RECUPERADO Y ACTUALIZADO ---

    // Relacion directa con el Usuario que tiene el juego actualmente.
    // nullable = true porque si el juego esta en la estanteria, no tiene "dueño" temporal.
    // Cambiamos socio_id por usuario_id para que coincida con tu nueva tabla 'usuarios'.
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario prestadoA;
}