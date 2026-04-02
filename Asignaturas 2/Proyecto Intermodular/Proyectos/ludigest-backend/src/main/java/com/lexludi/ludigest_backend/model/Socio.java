package com.lexludi.ludigest_backend.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Representa a los miembros de la asociacion segun nuestro Excel real
@Entity
@Table(name = "socios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;
    
 // Apodo o nombre de usuario en el foro de LexLudi
    @Column(length = 50)
    private String apodo;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    // Definimos los permisos del usuario ("SOCIO" o "ADMIN")
    @Column(nullable = false, length = 20)
    private String rol = "SOCIO";

    // Por defecto, un socio nuevo entra como activo en el sistema
    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaAlta;

    // Control para saber si el socio puede abrir el local por su cuenta
    @Column(nullable = false)
    private Boolean tieneLlave = false;

    // Para gestionar la tesoreria: "MENSUAL", "ANUAL", etc.
    @Column(nullable = false, length = 20)
    private String tipoCuota;
}