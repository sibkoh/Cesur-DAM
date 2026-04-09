package com.lexludi.ludigest_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lexludi.ludigest_backend.model.Ejemplar;
import com.lexludi.ludigest_backend.repository.EjemplarRepository;

// Servicio encargado de la logica de negocio del inventario fisico
@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    public EjemplarService(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    // Registra una nueva caja fisica en nuestra base de datos
    public Ejemplar guardarEjemplar(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    // Devuelve el listado completo de todas las cajas de la ludoteca
    public List<Ejemplar> obtenerTodos() {
        return ejemplarRepository.findAll();
    }

    // Devuelve solo las copias fisicas que tenemos de un juego especifico
    public List<Ejemplar> obtenerPorJuegoId(Long juegoId) {
        return ejemplarRepository.findByJuegoReferenciaId(juegoId);
    }
 // Añade este método en tu EjemplarService
    public List<Ejemplar> buscarEjemplares(String query) {
        return ejemplarRepository.buscarPorCodigoOTitulo(query);
    }
}