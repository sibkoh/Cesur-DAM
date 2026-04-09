package com.lexludi.ludigest_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.Ejemplar;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

    // Magia de Spring Data: busca todos los ejemplares fisicos que compartan el mismo ID de juego teorico
    List<Ejemplar> findByJuegoReferenciaId(Long juegoId);
    
 // Añade esta consulta dentro de tu interface EjemplarRepository
    @Query("SELECT e FROM Ejemplar e WHERE LOWER(e.codigoLocal) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(e.juegoReferencia.titulo) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Ejemplar> buscarPorCodigoOTitulo(@Param("query") String query);
}