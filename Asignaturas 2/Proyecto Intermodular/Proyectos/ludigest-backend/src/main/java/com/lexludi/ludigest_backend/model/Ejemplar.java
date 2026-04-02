package com.lexludi.ludigest_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// La caja fisica concreta que esta en nuestra ludoteca
@Entity
@Table(name = "ejemplares")
@Getter
@Setter
@NoArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamos esta caja con su ficha tecnica general
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_referencia_id")
    private JuegoReferencia juegoReferencia;

    // Codigo interno de la asociacion impreso en la pegatina de la caja
    @Column(nullable = false, unique = true, length = 50)
    private String codigoInterno;

    // Campo vital para controlar la cuarentena de 30 dias de LexLudi
    @Column(nullable = false)
    private LocalDate fechaAlta;

    // Para controlar si faltan piezas o la caja esta rota
    @Column(length = 255)
    private String notasConservacion;
    
    // Si esta prestado, en reparacion, o disponible
    @Column(nullable = false, length = 20)
    private String estadoActual;
}