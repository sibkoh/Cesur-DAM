package com.lexludi.ludigest_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.Ejemplar;
import com.lexludi.ludigest_backend.service.EjemplarService;

// Controlador para gestionar el inventario fisico (las cajas reales)
@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    // Inyectamos el servicio por constructor
    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    // GET a http://localhost:8081/api/ejemplares
    // Devuelve absolutamente todas las cajas
    @GetMapping
    public List<Ejemplar> listarTodos() {
        return ejemplarService.obtenerTodos();
    }

    // GET a http://localhost:8081/api/ejemplares/juego/{id}
    // Usamos @PathVariable para leer el ID de la URL y devolver solo las copias de ese juego
    @GetMapping("/juego/{juegoId}")
    public List<Ejemplar> listarPorJuego(@PathVariable Long juegoId) {
        return ejemplarService.obtenerPorJuegoId(juegoId);
    }

    // POST a http://localhost:8081/api/ejemplares
    // Recibe un JSON con los datos de la caja y la guarda
    @PostMapping
    public Ejemplar guardarEjemplar(@RequestBody Ejemplar nuevoEjemplar) {
        return ejemplarService.guardarEjemplar(nuevoEjemplar);
    }
}