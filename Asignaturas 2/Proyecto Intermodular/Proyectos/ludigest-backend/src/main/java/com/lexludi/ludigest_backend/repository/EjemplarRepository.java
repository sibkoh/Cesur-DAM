package com.lexludi.ludigest_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.Ejemplar;

// Repositorio para buscar y gestionar el inventario fisico de cajas
@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
}