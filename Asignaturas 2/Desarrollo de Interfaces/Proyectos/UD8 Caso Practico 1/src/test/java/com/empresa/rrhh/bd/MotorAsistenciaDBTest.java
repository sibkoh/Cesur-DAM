package com.empresa.rrhh.bd;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Importamos las librerías de JUnit 5
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de Pruebas Unitarias (Caja Blanca / Caja Negra) Su objetivo es probar
 * exhaustivamente los métodos públicos de MotorAsistenciaDB.
 */
class MotorAsistenciaDBTest {

	// Instancia de nuestra clase de base de datos que vamos a testear
	private MotorAsistenciaDB gestor;

	/**
	 * La anotación @BeforeEach le dice a JUnit: "Ejecuta este método
	 * automáticamente ANTES de CADA @Test". Es vital aislar los tests: el resultado
	 * del Test 1 no puede afectar al Test 2.
	 */
	@BeforeEach
	void prepararEntorno() {
		gestor = new MotorAsistenciaDB();
		// Vaciamos la tabla por completo. Empezamos desde cero cada vez.
		gestor.limpiarTablaPruebas();
	}

	// ---------------------------------------------------------
	// TEST OBLIGATORIO 1: Inserción correcta de nueva entrada
	// ---------------------------------------------------------
	@Test
	void testInsercionCorrectaNuevaEntrada() {
		// 1. Preparación de datos (Arrange)
		String empleado = "Laura Gomez";

		// 2. Ejecución de la acción (Act)
		boolean resultado = gestor.registrarEntrada(empleado);

		// 3. Comprobación (Assert)
		// Usamos assertTrue para confirmar que el método devolvió 'true' (éxito).
		// El texto que acompaña al Assert se mostrará en rojo en la consola si la
		// prueba falla.
		assertTrue(resultado, "Fallo: El registro de entrada debería ser exitoso para un usuario nuevo.");

		// Comprobación doble: Le preguntamos a la base de datos si Laura está dentro.
		assertTrue(gestor.yaTieneEntradaHoy(empleado),
				"Fallo: El sistema no detecta la entrada que acabamos de hacer.");
	}

	// ---------------------------------------------------------
	// TEST OBLIGATORIO 2: Inserción correcta de nueva salida
	// ---------------------------------------------------------
	@Test
	void testInsercionCorrectaNuevaSalida() {
		String empleado = "Carlos Ruiz";

		// Precondición: Para probar una salida, el usuario tiene que haber entrado
		// primero.
		// Si no hacemos esto, el sistema (correctamente) rechazaría la salida.
		gestor.registrarEntrada(empleado);

		// Intentamos registrar la salida
		boolean resultadoSalida = gestor.registrarSalida(empleado);

		// Verificamos que la salida fue aceptada
		assertTrue(resultadoSalida,
				"Fallo: La base de datos rechazó la salida a pesar de que el empleado había entrado.");
	}

	// ---------------------------------------------------------
	// TEST OBLIGATORIO 3: NO inserción de un usuario ya dado de entrada
	// ---------------------------------------------------------
	@Test
	void testNoInsercionUsuarioYaEntrado() {
		String empleado = "Marta Diaz";

		// Simulamos que Marta ficha por la mañana
		gestor.registrarEntrada(empleado);

		// Simulamos que Marta se equivoca y vuelve a pulsar "Entrada" a los 5 minutos
		boolean segundaEntrada = gestor.registrarEntrada(empleado);

		// Aquí usamos assertFalse. Queremos que el método devuelva FALSE (que rechace
		// la acción).
		// Si devuelve TRUE, significa que tenemos un bug y permite dobles fichajes.
		assertFalse(segundaEntrada, "Bug de Lógica: El sistema permitió fichar entrada dos veces el mismo día.");
	}

	// ---------------------------------------------------------
	// TEST OBLIGATORIO 4: NO inserción de un usuario ya dado de salida
	// ---------------------------------------------------------
	@Test
	void testNoInsercionUsuarioYaSalido() {
		String empleado = "Pablo Lopez";

		// Ciclo completo de trabajo de Pablo
		gestor.registrarEntrada(empleado);
		gestor.registrarSalida(empleado);

		// Pablo pulsa "Salida" otra vez antes de irse a casa por error
		boolean segundaSalida = gestor.registrarSalida(empleado);

		// Debe ser rechazado
		assertFalse(segundaSalida, "Bug de Lógica: El sistema permitió fichar salida dos veces.");
	}

	@Test
	void testSeguridadInyeccionSQL() {
		// Un atacante escribe este código en el campo 'Nombre' de la interfaz.
		// Su objetivo es "engañar" a nuestra consulta INSERT para que cierre las
		// comillas
		// y ejecute un comando dañino (como OR '1'='1' o un DROP TABLE).
		String payloadHacker = "Malo' OR '1'='1";

		// Ejecutamos la entrada con ese nombre peligroso
		boolean resultado = gestor.registrarEntrada(payloadHacker);

		// Si usamos 'PreparedStatement' correctamente en el Motor, la base de datos
		// NO interpretará las comillas como código SQL, sino como texto literal.
		// Por tanto, debe guardar a un empleado con un nombre muy raro y devolver TRUE.
		assertTrue(resultado,
				"Test de Seguridad Fallido: El sistema no pudo registrar el string con caracteres especiales.");

		// Verificamos que realmente se guardó literalmente así
		assertTrue(gestor.yaTieneEntradaHoy(payloadHacker),
				"Test de Seguridad Fallido: El payload alteró la consulta.");
	}
}