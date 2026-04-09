package com.lexludi.ludigest_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexludi.ludigest_backend.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Metodo magico de Spring Data para buscar un usuario por su nombre de acceso
    Optional<Usuario> findByUsername(String username);
}