package com.lexludi.ludigest_backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;

// Controlador para la ficha tecnica de los juegos de la ludoteca
@RestController
@RequestMapping("/api/juegos")
public class JuegoReferenciaController {

    private final JuegoReferenciaRepository juegoReferenciaRepository;

    public JuegoReferenciaController(JuegoReferenciaRepository juegoReferenciaRepository) {
        this.juegoReferenciaRepository = juegoReferenciaRepository;
    }

    // Metodo para consultar todo nuestro catalogo teorico
    @GetMapping
    public List<JuegoReferencia> listarTodos() {
        return juegoReferenciaRepository.findAll();
    }

    // Metodo para introducir la ficha de un nuevo juego (por ejemplo, leido desde BGG)
    @PostMapping
    public JuegoReferencia guardarJuego(@RequestBody JuegoReferencia nuevoJuego) {
        return juegoReferenciaRepository.save(nuevoJuego);
    }
}