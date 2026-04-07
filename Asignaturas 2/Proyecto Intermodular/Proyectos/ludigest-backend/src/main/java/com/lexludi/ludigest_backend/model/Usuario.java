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

// Entidad unificada para gestionar tanto el acceso al sistema como los datos de la asociacion
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- DATOS DE ACCESO (LOGIN) ---

    // El nombre de usuario para hacer login (debe ser unico)
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // La contrasena de acceso
    @Column(nullable = false, length = 255)
    private String password;

    // Nivel de permisos en la aplicacion (ADMIN, SOCIO, etc.)
    @Column(nullable = false, length = 20)
    private String rol = "SOCIO";

    // --- DATOS PERSONALES (Fusionados) ---

    // Sin nullable = false para evitar errores de migracion con usuarios antiguos
    @Column(length = 100)
    private String nombre;

    @Column(length = 150)
    private String apellidos;

    // Nombre por el que se le conoce en las mesas de juego (opcional)
    @Column(length = 50)
    private String apodo;

    // Correo electronico del usuario (unico)
    @Column(unique = true, length = 100)
    private String email;

    // Telefono de contacto del administrador/socio (opcional)
    @Column(length = 15)
    private String telefono;

    // --- DATOS DE GESTION DE LA ASOCIACION ---

    // Indica si la persona puede participar en las actividades y llevarse juegos
    @Column(nullable = false)
    private Boolean activo = true;

    // Fecha en la que la persona se inscribio en LexLudi
    private LocalDate fechaAlta;

    // Control de acceso fisico al local
    @Column(nullable = false)
    private Boolean tieneLlave = false;

    // Tipo de suscripcion (ej: Anual, Semestral, Familiar)
    @Column(length = 50)
    private String tipoCuota;
}