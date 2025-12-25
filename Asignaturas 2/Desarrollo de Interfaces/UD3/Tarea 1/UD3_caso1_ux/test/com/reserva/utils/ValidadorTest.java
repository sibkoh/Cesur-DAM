package com.reserva.utils; // <--- Permite hacer test sobre la clase Validator que está en ese paquete

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// al estar en el mismo paquete, 
// ni siquiera hace falta importar la clase Validador.

class ValidadorTest {
// --- PRUEBAS PARA CAMPOS VACÍOS ---

	@Test
	void testCampoVacioDetectaNulos() {
		// Debe devolver TRUE si le paso un null
		assertTrue(Validador.esCampoVacio(null), "Un null debe contar como vacío");
	}

	@Test
	void testCampoVacioDetectaEspacios() {
		// Debe devolver TRUE si solo hay espacios en blanco
		assertTrue(Validador.esCampoVacio("   "), "Espacios en blanco deben contar como vacío");
	}

	@Test
	void testCampoVacioAceptaTexto() {
		// Debe devolver FALSE si hay texto real
		assertFalse(Validador.esCampoVacio("Juan"), "El texto normal no es vacío");
	}

	// --- PRUEBAS PARA EMAIL ---

	@Test
	void testEmailCorrecto() {
		// Caso de éxito
		assertTrue(Validador.esEmailValido("usuario@empresa.es"), "Debe aceptar .es");
	}

	@Test
	void testEmailIncorrectoExtension() {
		// Falla por ser .com
		assertFalse(Validador.esEmailValido("usuario@empresa.com"), "No debe aceptar .com");
	}

	@Test
	void testEmailSinArroba() {
		// Falla por formato
		assertFalse(Validador.esEmailValido("usuarioempresa.es"), "Debe requerir arroba");
	}

	@Test
	void testEmailVacio() {
		assertFalse(Validador.esEmailValido(""), "Un string vacío no es un email");
	}
}
