package com.lexludi.ludigest_backend.service;

import com.lexludi.ludigest_backend.model.Ejemplar;
import com.lexludi.ludigest_backend.model.Prestamo;
import com.lexludi.ludigest_backend.model.Socio;
import com.lexludi.ludigest_backend.repository.EjemplarRepository;
import com.lexludi.ludigest_backend.repository.PrestamoRepository;
import com.lexludi.ludigest_backend.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final SocioRepository socioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, 
                           EjemplarRepository ejemplarRepository, 
                           SocioRepository socioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.socioRepository = socioRepository;
    }

    @Transactional
    public Prestamo realizarPrestamo(Long socioId, Long ejemplarId) {
        
        // 1. Buscamos el ejemplar y al socio
        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() -> new RuntimeException("Error: Ejemplar no encontrado con ID " + ejemplarId));
        
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new RuntimeException("Error: Socio no encontrado con ID " + socioId));

        // --- REGLAS DE NEGOCIO LEXLUDI ---

        if ("RESERVADO".equals(ejemplar.getEstado())) {
            throw new RuntimeException("Este ejemplar esta RESERVADO para un evento y no se puede prestar");
        }

        if (!"DISPONIBLE".equals(ejemplar.getEstado())) {
            throw new RuntimeException("Operacion denegada: El ejemplar no esta disponible actualmente");
        }

        LocalDate fechaCorteCuarentena = LocalDate.now().minusDays(30);
        if (ejemplar.getFechaAdquisicion().isAfter(fechaCorteCuarentena)) {
            throw new RuntimeException("Este juego es una NOVEDAD y esta en cuarentena; no puede salir del local");
        }

        long prestamosActivosSocio = prestamoRepository.countBySocioAndActivoTrue(socio);
        if (prestamosActivosSocio >= 5) {
            throw new RuntimeException("El socio ya tiene el maximo de 5 juegos permitidos");
        }

        // --- CREACION DEL PRESTAMO ---

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setSocio(socio);
        nuevoPrestamo.setEjemplar(ejemplar);
        nuevoPrestamo.setFechaPrestamo(LocalDate.now());
        nuevoPrestamo.setFechaLimite(LocalDate.now().plusDays(30));
        
        // AQUI ESTA LA CLAVE QUE COMENTABAS: Forzamos el true de forma explicita
        nuevoPrestamo.setActivo(true);

        ejemplar.setEstado("PRESTADO");
        ejemplarRepository.save(ejemplar);

        return prestamoRepository.save(nuevoPrestamo);
    }

    @Transactional
    public Prestamo devolverPrestamo(Long prestamoId, String anotacionesDevolucion) {
        
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Error: No existe el prestamo con ID " + prestamoId));

        if (!prestamo.getActivo()) {
            throw new RuntimeException("Operacion denegada: Este prestamo ya figura como inactivo.");
        }

        prestamo.setActivo(false);
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setAnotaciones(anotacionesDevolucion);

        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado("DISPONIBLE");
        ejemplarRepository.save(ejemplar);

        return prestamoRepository.save(prestamo);
    }
}