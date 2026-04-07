package com.lexludi.ludigest_backend.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexludi.ludigest_backend.model.Ejemplar;
import com.lexludi.ludigest_backend.model.Prestamo;
import com.lexludi.ludigest_backend.model.Usuario;
import com.lexludi.ludigest_backend.repository.EjemplarRepository;
import com.lexludi.ludigest_backend.repository.PrestamoRepository;
import com.lexludi.ludigest_backend.repository.UsuarioRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, 
                           EjemplarRepository ejemplarRepository, 
                           UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Prestamo realizarPrestamo(Long usuarioId, Long ejemplarId) {
        
        // 1. Buscamos el ejemplar y al usuario
        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() -> new RuntimeException("Error: Ejemplar no encontrado con ID " + ejemplarId));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado con ID " + usuarioId));

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

        long prestamosActivosUsuario = prestamoRepository.countByUsuarioAndActivoTrue(usuario);
        if (prestamosActivosUsuario >= 5) {
            throw new RuntimeException("El usuario ya tiene el maximo de 5 juegos permitidos");
        }

        // --- CREACION DEL PRESTAMO ---

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setUsuario(usuario);
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