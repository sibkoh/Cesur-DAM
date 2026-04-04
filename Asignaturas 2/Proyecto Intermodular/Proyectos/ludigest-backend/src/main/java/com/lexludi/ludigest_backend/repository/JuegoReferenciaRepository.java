package com.lexludi.ludigest_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.JuegoReferencia;

@Repository
public interface JuegoReferenciaRepository extends JpaRepository<JuegoReferencia, Long> {

    // Buscamos un juego por su ID unico de la BGG para evitar duplicados
    // Usamos Optional para gestionar de forma elegante si el juego no existe todavia
    Optional<JuegoReferencia> findByIdBgg(Long idBgg);
}