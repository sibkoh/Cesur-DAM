package com.lexludi.ludigest_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.JuegoReferencia;

// Repositorio para acceder al catalogo general de juegos
@Repository
public interface JuegoReferenciaRepository extends JpaRepository<JuegoReferencia, Long> {
}