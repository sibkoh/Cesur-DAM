package com.lexludi.ludigest_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.Socio;

// Repositorio para gestionar las operaciones de base de datos de los socios
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
}