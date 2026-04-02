package com.lexludi.ludigest_backend.controller;

import com.lexludi.ludigest_backend.model.Prestamo;
import com.lexludi.ludigest_backend.repository.PrestamoRepository;
import com.lexludi.ludigest_backend.service.PrestamoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoRepository prestamoRepository;
    // Anadimos nuestro nuevo servicio
    private final PrestamoService prestamoService;

    // Actualizamos el constructor para inyectar ambos
    public PrestamoController(PrestamoRepository prestamoRepository, PrestamoService prestamoService) {
        this.prestamoRepository = prestamoRepository;
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    @PostMapping
    public Prestamo guardarPrestamo(@RequestBody Prestamo nuevoPrestamo) {
        return prestamoRepository.save(nuevoPrestamo);
    }

    // --- NUEVO ENDPOINT CON LOGICA DE NEGOCIO ---
    
    // Al usar RequestParam en Postman mandaremos la peticion asi: 
    // POST http://localhost:8081/api/prestamos/realizar?socioId=1&ejemplarId=3
    @PostMapping("/realizar")
    public Prestamo realizarPrestamo(@RequestParam Long socioId, @RequestParam Long ejemplarId) {
        // Delegamos todo el trabajo pesado a nuestra capa de servicios
        return prestamoService.realizarPrestamo(socioId, ejemplarId);
    }
    
 // Endpoint para realizar la devolucion de un ejemplar
    // Ejemplo: POST http://localhost:8081/api/prestamos/devolver/5?anotaciones=Todo en perfecto estado
    @PostMapping("/devolver/{id}")
    public Prestamo devolverPrestamo(@PathVariable Long id, 
                                     @RequestParam(required = false) String anotaciones) {
        // Delegamos la logica de cambio de estados al servicio
        return prestamoService.devolverPrestamo(id, anotaciones);
    }
}

