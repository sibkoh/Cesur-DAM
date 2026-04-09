package com.lexludi.ludigest_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexludi.ludigest_backend.dto.BggGameDetailsDto;
import com.lexludi.ludigest_backend.dto.BggSearchDto;
import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.service.BggSyncService;
import com.lexludi.ludigest_backend.service.JuegoReferenciaService;

import jakarta.validation.Valid;

// Controlador para la ficha tecnica de los juegos de la ludoteca
@RestController
@RequestMapping("/api/juegos")
public class JuegoReferenciaController {

    private final JuegoReferenciaService juegoReferenciaService;
    private final BggSyncService bggSyncService;

    public JuegoReferenciaController(JuegoReferenciaService juegoReferenciaService, 
                                     BggSyncService bggSyncService) {
        this.juegoReferenciaService = juegoReferenciaService;
        this.bggSyncService = bggSyncService;
    }

    // --- ENDPOINTS DE BASE DE DATOS LOCAL ---

    @GetMapping
    public List<JuegoReferencia> listarTodos() {
        return juegoReferenciaService.obtenerTodos();
    }

    @PostMapping
    public JuegoReferencia guardarJuego(@Valid @RequestBody JuegoReferencia nuevoJuego) {
        return juegoReferenciaService.guardarJuego(nuevoJuego);
    }

    // --- ENDPOINTS DE INTEGRACION EXTERNA (BGG) ---

    // GET a http://localhost:8081/api/juegos/bgg/buscar?query=catan
    @GetMapping("/bgg/buscar")
    public List<BggSearchDto> buscarEnBgg(@RequestParam String query) {
        return bggSyncService.buscarJuegos(query);
    }

    // GET a http://localhost:8081/api/juegos/bgg/detalles?id=13
    @GetMapping("/bgg/detalles")
    public BggGameDetailsDto detallesDeBgg(@RequestParam Long id) {
        return bggSyncService.obtenerDetallesJuego(id);
    }
    
 // Nuevo endpoint de importación pesada
    // Recibe: {"bggId": 13}
    @PostMapping("/bgg/importar")
    public JuegoReferencia importarDesdeBgg(@RequestBody ImportRequest request) {
        // Llamada limpia y directa
        return bggSyncService.importarJuegoDesdeBgg(request.bggId());
    }

    // Pequeño DTO interno para mapear la petición del Front
    public record ImportRequest(Long bggId) {}
}