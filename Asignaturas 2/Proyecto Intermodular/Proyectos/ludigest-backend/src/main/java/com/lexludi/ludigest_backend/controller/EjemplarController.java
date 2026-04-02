package com.lexludi.ludigest_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.Ejemplar;
import com.lexludi.ludigest_backend.repository.EjemplarRepository;

// Exponemos el inventario fisico hacia el exterior (Postman o frontend)
@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarRepository ejemplarRepository;

    // Inyectamos el repositorio por constructor para asegurar que siempre este disponible
    public EjemplarController(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    // Metodo para recuperar todos los ejemplares de nuestras estanterias
    @GetMapping
    public List<Ejemplar> listarTodos() {
        return ejemplarRepository.findAll();
    }

    // Metodo para registrar la entrada de una nueva caja al local
    @PostMapping
    public Ejemplar guardarEjemplar(@RequestBody Ejemplar nuevoEjemplar) {
        // En el futuro, aqui anadiremos la logica para auto-generar el codigoLocal
        return ejemplarRepository.save(nuevoEjemplar);
    }
}