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

// Entidad para gestionar el acceso al panel de administracion
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El nombre de usuario para hacer login (debe ser unico)
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // La contrasena de acceso
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    // --- NUEVOS CAMPOS APLICADOS ---

    // Correo electronico del usuario para notificaciones o recuperacion (obligatorio y unico)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Telefono de contacto del administrador (opcional)
    @Column(nullable = true, length = 15)
    private String telefono;

    // Por ahora, todos los que entren seran administradores
    @Column(nullable = false, length = 20)
    private String rol = "ADMIN";
}