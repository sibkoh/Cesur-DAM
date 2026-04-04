package com.lexludi.ludigest_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;
import com.lexludi.ludigest_backend.service.BggSyncService;

// Controlador para la ficha tecnica de los juegos de la ludoteca
@RestController
@RequestMapping("/api/juegos")
public class JuegoReferenciaController {

    private final JuegoReferenciaRepository juegoReferenciaRepository;
    
    // Anadimos nuestro nuevo servicio encargado de hablar con la BGG
    private final BggSyncService bggSyncService;

    // Actualizamos el constructor para inyectar tanto el repositorio como el servicio
    public JuegoReferenciaController(JuegoReferenciaRepository juegoReferenciaRepository, 
                                     BggSyncService bggSyncService) {
        this.juegoReferenciaRepository = juegoReferenciaRepository;
        this.bggSyncService = bggSyncService;
    }

    // Metodo para consultar todo nuestro catalogo teorico
    @GetMapping
    public List<JuegoReferencia> listarTodos() {
        return juegoReferenciaRepository.findAll();
    }

    // Metodo para introducir la ficha de un nuevo juego manualmente
    @PostMapping
    public JuegoReferencia guardarJuego(@RequestBody JuegoReferencia nuevoJuego) {
        return juegoReferenciaRepository.save(nuevoJuego);
    }

    // --- NUEVO ENDPOINT PARA TESTEAR LA IMPORTACION XML ---
    
    // Al hacer un GET a http://localhost:8081/api/juegos/importar-mock
    // probaremos que nuestro servicio lee bien el archivo y lo transforma a JSON
    @GetMapping("/importar-mock")
    public JuegoReferencia importarMock() {
        // Delegamos todo el trabajo de lectura al servicio y devolvemos el resultado
        return bggSyncService.importarJuegoMock();
    }
}