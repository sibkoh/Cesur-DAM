package com.lexludi.ludigest_backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.Socio;
import com.lexludi.ludigest_backend.repository.SocioRepository;

// Controlador para gestionar los endpoints de nuestra API relacionados con los socios
@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioRepository socioRepository;

    // Inyectamos el repositorio a traves del constructor (buena practica en Spring)
    public SocioController(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    // Endpoint para obtener la lista completa de socios registrados
    @GetMapping
    public List<Socio> listarTodos() {
        // Usamos el metodo findAll que nos regala JpaRepository
        return socioRepository.findAll();
    }

    // Endpoint para dar de alta un nuevo socio desde Postman
    @PostMapping
    public Socio guardarSocio(@RequestBody Socio nuevoSocio) {
        // Guardamos el objeto que nos llega en JSON y devolvemos el registro creado con su ID
        return socioRepository.save(nuevoSocio);
    }
}