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

// Entidad que registra el historico y el estado actual de los prestamos en la asociacion
@Entity
@Table(name = "prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un socio puede tener varios prestamos registrados en su historial
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socio;

    // Un ejemplar fisico puede estar en varios prestamos a lo largo del tiempo (historial)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ejemplar_id")
    private Ejemplar ejemplar;

    // Fecha en la que el juego sale de la ludoteca
    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    // Fecha tope para devolverlo sin saltar las alarmas
    @Column(nullable = false)
    private LocalDate fechaLimite;

    // Fecha real de entrega. Es nula (nullable) mientras el juego siga prestado
    private LocalDate fechaDevolucion;

    // Control rapido para saber si el prestamo sigue en curso (true) o ya termino (false)
    @Column(nullable = false)
    private Boolean activo = true;

    // Espacio para apuntar si el socio devuelve el juego con cartas dobladas, caja rota, etc.
    @Column(length = 500)
    private String anotaciones;
}