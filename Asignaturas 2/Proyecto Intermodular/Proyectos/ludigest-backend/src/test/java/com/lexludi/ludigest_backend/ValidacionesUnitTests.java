package com.lexludi.ludigest_backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.model.Usuario;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

// hemos quitado el @SpringBootTest! Ahora es un test unitario puro y a prueba de balas.
class ValidacionesUnitTests {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // TEST 1: Validación de tamaño de nombre de usuario
    @Test
    void testUsuarioUsernameSizeValidation() {
        Usuario usuario = new Usuario();
        usuario.setUsername("ab"); // Dato inválido (< 4 caracteres)
        usuario.setPassword("secreta123");
        usuario.setRol("SOCIO");
        usuario.setActivo(true);
        usuario.setTieneLlave(false);

        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);

        assertFalse(violaciones.isEmpty(), "El validador debería haber detectado errores");
        boolean falloUsername = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username"));
        assertTrue(falloUsername, "Debe saltar la validación @Size en el campo username");
    }

    // TEST 2: Validación de ID BGG obligatorio
    @Test
    void testJuegoReferenciaIdBggNotNullValidation() {
        JuegoReferencia juego = new JuegoReferencia();
        // Falla porque idBgg es null
        juego.setTitulo("Catan");
        juego.setMinJugadores(3);
        juego.setMaxJugadores(4);
        juego.setDuracionMinutos(120);

        Set<ConstraintViolation<JuegoReferencia>> violaciones = validator.validate(juego);

        assertFalse(violaciones.isEmpty(), "Debe detectar la falta del ID");
        boolean falloIdBgg = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("idBgg"));
        assertTrue(falloIdBgg, "Debe saltar la validación @NotNull en el campo idBgg");
    }

    // TEST 3 (Sustituto de contextLoads): Validación de Título vacío
    @Test
    void testJuegoReferenciaTituloNotBlankValidation() {
        JuegoReferencia juego = new JuegoReferencia();
        juego.setIdBgg(13L);
        juego.setTitulo(""); // Dato inválido (vacío)
        juego.setMinJugadores(3);
        juego.setMaxJugadores(4);
        juego.setDuracionMinutos(120);

        Set<ConstraintViolation<JuegoReferencia>> violaciones = validator.validate(juego);

        assertFalse(violaciones.isEmpty(), "Debe detectar el título vacío");
        boolean falloTitulo = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));
        assertTrue(falloTitulo, "Debe saltar la validación @NotBlank en el campo titulo");
    }
}