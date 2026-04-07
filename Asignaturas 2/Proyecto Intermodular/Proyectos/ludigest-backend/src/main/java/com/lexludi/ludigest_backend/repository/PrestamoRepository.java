package com.lexludi.ludigest_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.Prestamo;
import com.lexludi.ludigest_backend.model.Usuario;

//Repositorio para el historial y control de prestamos activos e inactivos
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    // Contamos cuantos prestamos tiene abiertos un socio especifico
    long countByUsuarioAndActivoTrue(Usuario usuario);
}