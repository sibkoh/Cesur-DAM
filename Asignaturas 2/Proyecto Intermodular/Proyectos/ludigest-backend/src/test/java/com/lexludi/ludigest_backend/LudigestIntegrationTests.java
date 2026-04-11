package com.lexludi.ludigest_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;

// @SpringBootTest levanta TODO el servidor y se conecta a MySQL
@SpringBootTest
class LudigestIntegrationTests {

    // Inyectamos el repositorio real que habla con la base de datos
    @Autowired
    private JuegoReferenciaRepository juegoRepository;

    // TEST 1: Verifica que la conexión a la base de datos funciona y Spring arranca
    @Test
    void contextLoads() {
        // Si XAMPP está encendido y los datos del application.properties son correctos, esto pasará a verde.
    }

    // TEST 2: Prueba real de base de datos (Crear, Buscar, y Rollback)
    // @Transactional en un test hace magia: al terminar el test, deshace los cambios (Rollback)
    // para no ensuciar tu base de datos real con datos de prueba.
    @Test
    @Transactional
    void testGuardarYBuscarJuegoEnBaseDeDatos() {
        // 1. Arrange: Preparamos un juego de prueba
        JuegoReferencia juegoTest = new JuegoReferencia();
        juegoTest.setIdBgg(999999L); // Un ID inventado
        juegoTest.setTitulo("Juego de Prueba Integración");
        juegoTest.setMinJugadores(2);
        juegoTest.setMaxJugadores(4);
        juegoTest.setDuracionMinutos(30);

        // 2. Act: Guardamos en MySQL
        JuegoReferencia juegoGuardado = juegoRepository.save(juegoTest);
        
        // Buscamos el juego recién guardado por su ID de la BGG
        Optional<JuegoReferencia> juegoEncontrado = juegoRepository.findByIdBgg(999999L);

        // 3. Assert: Comprobamos que el viaje a la base de datos fue exitoso
        assertTrue(juegoEncontrado.isPresent(), "El juego debería haberse guardado en MySQL");
        assertEquals("Juego de Prueba Integración", juegoEncontrado.get().getTitulo(), "El título debe coincidir");
    }
}