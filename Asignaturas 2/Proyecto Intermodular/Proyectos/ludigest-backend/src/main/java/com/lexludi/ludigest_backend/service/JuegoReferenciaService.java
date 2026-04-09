package com.lexludi.ludigest_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;

// Servicio encargado de la logica de negocio de nuestro catalogo de juegos
@Service
public class JuegoReferenciaService {

    private final JuegoReferenciaRepository juegoReferenciaRepository;

    public JuegoReferenciaService(JuegoReferenciaRepository juegoReferenciaRepository) {
        this.juegoReferenciaRepository = juegoReferenciaRepository;
    }

    // Metodo para guardar un nuevo juego en el catalogo
    public JuegoReferencia guardarJuego(JuegoReferencia juego) {
        return juegoReferenciaRepository.save(juego);
    }

    // Metodo para recuperar toda la lista de juegos teoricos
    public List<JuegoReferencia> obtenerTodos() {
        return juegoReferenciaRepository.findAll();
    }

    // Metodo para buscar un juego en concreto por su ID interno
    public JuegoReferencia obtenerPorId(Long id) {
        // Utilizamos orElseThrow para manejar el caso en el que el ID no exista en MySQL
        return juegoReferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Juego no encontrado con ID " + id));
    }
    
 // Método para buscar por título
    public List<JuegoReferencia> buscarPorTitulo(String query) {
        return juegoReferenciaRepository.findByTituloContainingIgnoreCase(query);
    }
}