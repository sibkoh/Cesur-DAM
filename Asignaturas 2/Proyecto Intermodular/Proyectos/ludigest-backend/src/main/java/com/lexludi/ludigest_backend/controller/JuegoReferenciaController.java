package com.lexludi.ludigest_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.service.BggSyncService;
import com.lexludi.ludigest_backend.service.JuegoReferenciaService;

// Controlador para la ficha tecnica de los juegos de la ludoteca
@RestController
@RequestMapping("/api/juegos")
public class JuegoReferenciaController {

    // Inyectamos el nuevo servicio de logica de negocio (sustituye al uso directo del repositorio)
    private final JuegoReferenciaService juegoReferenciaService;
    
    // Mantenemos nuestro servicio encargado de hablar con la BGG
    private final BggSyncService bggSyncService;

    // Actualizamos el constructor para inyectar ambos servicios
    public JuegoReferenciaController(JuegoReferenciaService juegoReferenciaService, 
                                     BggSyncService bggSyncService) {
        this.juegoReferenciaService = juegoReferenciaService;
        this.bggSyncService = bggSyncService;
    }

    // Metodo para consultar todo nuestro catalogo teorico delegando en el servicio
    @GetMapping
    public List<JuegoReferencia> listarTodos() {
        return juegoReferenciaService.obtenerTodos();
    }

    // Metodo para introducir la ficha de un nuevo juego manualmente delegando en el servicio
    @PostMapping
    public JuegoReferencia guardarJuego(@RequestBody JuegoReferencia nuevoJuego) {
        return juegoReferenciaService.guardarJuego(nuevoJuego);
    }

    // --- ENDPOINT HISTORICO PARA TESTEAR LA IMPORTACION XML ---
    
    // Al hacer un GET a http://localhost:8081/api/juegos/importar-mock
    // probaremos que nuestro servicio lee bien el archivo y lo transforma a JSON
    @GetMapping("/importar-mock")
    public JuegoReferencia importarMock() {
        // Delegamos todo el trabajo de lectura al servicio y devolvemos el resultado
        return bggSyncService.importarJuegoMock();
    }
}