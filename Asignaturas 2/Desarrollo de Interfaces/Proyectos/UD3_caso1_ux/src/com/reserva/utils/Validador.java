package com.reserva.utils;

public class Validador {

	// Método estático: No necesita instanciar la clase para usarse
	// Método privado para validar el email con Regex
	// El enunciado pide estructura: XXXX@XXXX.es

	public static boolean esEmailValido(String email) {
		return email != null && email.matches("^[\\w-\\.]+@[\\w-]+\\.es$");

		// Explicación del Regex:
		// ^ -> Empieza aquí
		// [\\w-\\.]+ -> Cualquier letra, número, guión o punto (antes de la @)
		// @ -> Una arroba obligatoria
		// [\\w-]+ -> Cualquier letra, número o guión (el dominio)
		// \\.es -> Un punto seguido de "es" OBLIGATORIO
		// $ -> Termina aquí
	}

	// Método para comprobar si un campo está vacío o solo tiene espacios

	public static boolean esCampoVacio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}
}
