package es.cesur.dam.ad;

import java.util.List;
import java.util.Scanner;

import es.cesur.dam.ad.dao.GestorMatriculas;
import es.cesur.dam.ad.entidades.FichaInscripcion;
import es.cesur.dam.ad.util.HibernateUtil;

/**
 * CLASE PRINCIPAL (INTERFAZ DE USUARIO) Esta clase es la cara visible del
 * programa. Se encarga de pintar los menús, pedir datos al usuario y mostrar
 * las tablas.
 */
public class AppAcademia {

	// --- HERRAMIENTAS GLOBALES ---
	// Las definimos como 'static' para poder usarlas desde cualquier parte del
	// main.
	static Scanner teclado = new Scanner(System.in);
	static GestorMatriculas gestor = new GestorMatriculas(); // Nuestro "mayordomo" de base de datos

	public static void main(String[] args) {

		// TRUCO: Arrancamos Hibernate al principio.
		// Si no hacemos esto, la primera vez que intentemos guardar algo tardará unos
		// segundos.
		// Así la carga se hace mientras el usuario lee el menú.
		HibernateUtil.getSessionFactory();

		int opcion = -1;

		// BUCLE PRINCIPAL
		// Mantiene el programa encendido hasta que el usuario elija la opción 0.
		do {
			mostrarMenu();
			opcion = pedirEntero(" Selecciona una opción: ");

			// EL CEREBRO DEL MENÚ
			switch (opcion) {
			case 1:
				opcionAlta(); // Crear
				break;
			case 2:
				opcionBaja(); // Borrar
				break;
			case 3:
				opcionModificacion(); // Editar (Completo)
				break;
			case 4:
				opcionListar(); // Ver tabla bonita
				break;
			case 5:
				opcionEstadisticaSQL(); // Consulta SQL extra
				break;
			case 0:
				System.out.println(" Cerrando sistema... ¡Hasta pronto!");
				break;
			default:
				System.out.println(" Opción no válida. Prueba con un número del 0 al 5.");
			}
		} while (opcion != 0);

		// LIMPIEZA FINAL
		// Muy importante: Cerrar Hibernate y el teclado para liberar memoria del
		// ordenador.
		HibernateUtil.shutdown();
		teclado.close();
	}

	// Método simple para pintar las opciones en pantalla
	private static void mostrarMenu() {
		System.out.println("\n========================================");
		System.out.println("    GESTIÓN ACADÉMICA   ");
		System.out.println("========================================");
		System.out.println("1. Matricular Alumno (Alta)");
		System.out.println("2. Dar de Baja Alumno (Baja)");
		System.out.println("3. Modificar Ficha (Edición Completa)");
		System.out.println("4. Listar Alumnos (Vista Tabla)");
		System.out.println("5. Ver Edad Media (Ejemplo de uso SQL Nativo)");
		System.out.println("0. Salir");
		System.out.println("----------------------------------------");
	}

	// --- OPCIÓN 1: ALTA (CREATE) ---
	private static void opcionAlta() {
		System.out.println("\n---  NUEVA MATRÍCULA ---");

		// Pedimos los datos uno a uno
		System.out.print("Nombre: ");
		String nombre = teclado.nextLine();

		System.out.print("Apellidos: ");
		String apellidos = teclado.nextLine();

		System.out.print("Curso: ");
		String curso = teclado.nextLine();

		// Para números usamos nuestro método seguro (así no falla si escriben letras)
		int edad = pedirEntero("Edad: ");
		int asig = pedirEntero("Nº Asignaturas: ");

		// Empaquetamos todo en un objeto y se lo damos al Gestor para que lo guarde
		FichaInscripcion alumno = new FichaInscripcion(nombre, apellidos, curso, edad, asig);
		gestor.altaAlumno(alumno);
	}

	// --- OPCIÓN 2: BAJA (DELETE) ---
	private static void opcionBaja() {
		// Mostramos la lista primero para que el usuario vea los IDs disponibles
		opcionListar();
		System.out.println("\n---  DAR DE BAJA ---");
		int id = pedirEntero("Introduce el ID del alumno a borrar: ");

		// Intentamos borrar. El método devuelve 'true' si lo consigue.
		if (gestor.borrarAlumno((long) id)) {
			System.out.println(" Baja procesada correctamente.");
		} else {
			System.out.println(" Error: No existe ningún alumno con ese ID.");
		}
	}

	// --- OPCIÓN 3: MODIFICAR (UPDATE) - ¡IMPORTANTE! ---
	private static void opcionModificacion() {
		opcionListar();
		System.out.println("\n---  MODIFICAR ALUMNO ---");
		int id = pedirEntero("Introduce el ID del alumno a modificar: ");

		// Paso 1: Traemos el alumno de la base de datos a la memoria Java
		FichaInscripcion alumno = gestor.obtenerAlumno((long) id);

		if (alumno != null) {
			System.out.println("\n--- MODO EDICIÓN (Pulsa ENTER para dejar el dato igual) ---");

			// Lógica para Texto: Leemos línea. Si NO está vacía, actualizamos. Si está
			// vacía, no hacemos nada.

			// 1. Nombre
			System.out.print("Nombre actual [" + alumno.getNombre() + "]: ");
			String nuevoNombre = teclado.nextLine();
			if (!nuevoNombre.isEmpty())
				alumno.setNombre(nuevoNombre);

			// 2. Apellidos
			System.out.print("Apellidos actuales [" + alumno.getApellidos() + "]: ");
			String nuevosApellidos = teclado.nextLine();
			if (!nuevosApellidos.isEmpty())
				alumno.setApellidos(nuevosApellidos);

			// 3. Curso
			System.out.print("Curso actual [" + alumno.getCurso() + "]: ");
			String nuevoCurso = teclado.nextLine();
			if (!nuevoCurso.isEmpty())
				alumno.setCurso(nuevoCurso);

			// Lógica para Números: Es más difícil porque 'nextLine' devuelve texto.
			// Leemos como texto -> Si no está vacío -> Intentamos convertir a número.

			// 4. Edad
			System.out.print("Edad actual [" + alumno.getEdad() + "]: ");
			String nuevaEdadTexto = teclado.nextLine();
			if (!nuevaEdadTexto.isEmpty()) {
				try {
					alumno.setEdad(Integer.parseInt(nuevaEdadTexto));
				} catch (NumberFormatException e) {
					System.out.println(" Dato no válido. Se mantiene la edad anterior.");
				}
			}

			// 5. Asignaturas
			System.out.print("Asignaturas actuales [" + alumno.getNumAsignaturas() + "]: ");
			String nuevasAsigTexto = teclado.nextLine();
			if (!nuevasAsigTexto.isEmpty()) {
				try {
					alumno.setNumAsignaturas(Integer.parseInt(nuevasAsigTexto));
				} catch (NumberFormatException e) {
					System.out.println(" Dato no válido. Se mantienen las asignaturas anteriores.");
				}
			}

			// Paso Final: Enviamos el objeto modificado de vuelta a la base de datos
			gestor.actualizarAlumno(alumno);

		} else {
			System.out.println(" Alumno no encontrado.");
		}
	}

	// --- OPCIÓN 4: LISTAR (READ) - VISUALIZACIÓN TABLA ---
	private static void opcionListar() {
		System.out.println("\n---  LISTADO DE ALUMNOS ---");
		List<FichaInscripcion> lista = gestor.obtenerTodos();

		if (lista.isEmpty()) {
			System.out.println(" La base de datos está vacía.");
		} else {
			// EXPLICACIÓN DEL FORMATO 'printf':
			// %-5s -> String alineado a la izquierda, ocupando 5 espacios.
			// %-15s -> String alineado a la izquierda, ocupando 15 espacios.
			// %n -> Salto de línea.

			// Pintamos la cabecera
			System.out.printf("%-5s | %-15s | %-20s | %-10s | %-5s | %-5s%n", "ID", "NOMBRE", "APELLIDOS", "CURSO",
					"EDAD", "ASIG.");
			System.out.println("--------------------------------------------------------------------------");

			// Pintamos cada fila
			for (FichaInscripcion a : lista) {
				System.out.printf("%-5d | %-15s | %-20s | %-10s | %-5d | %-5d%n", a.getId(), cortar(a.getNombre(), 15),

						// Usamos 'cortar' para que si el nombre es muy largo no rompa la tabla

						cortar(a.getApellidos(), 20), cortar(a.getCurso(), 10), a.getEdad(), a.getNumAsignaturas());
			}
			System.out.println("--------------------------------------------------------------------------");
		}
	}

	// --- OPCIÓN 5: ESTADÍSTICA (USO DE SQL NATIVO) ---
	private static void opcionEstadisticaSQL() {
		System.out.println("\n---  ESTADÍSTICAS ---");
		Double media = gestor.obtenerEdadMedia();

		if (media != null && media > 0) {
			// %.2f -> Muestra solo 2 decimales
			System.out.printf("La edad media de los alumnos es: %.2f años\n", media);
		} else {
			System.out.println("No hay suficientes datos para calcular la media.");
		}
	}

	// --- MÉTODOS AYUDANTES (Para que el código principal sea más limpio) ---

	// 1. Pide un número y no te deja salir hasta que escribes uno válido.
	private static int pedirEntero(String mensaje) {
		while (true) {
			try {
				System.out.print(mensaje);
				return Integer.parseInt(teclado.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(" Error: Por favor, introduce un número válido.");
			}
		}
	}

	// 2. Corta textos largos. Si reservamos 10 espacios y el nombre mide 20, la
	// tabla se rompería.
	// Esto corta el texto y le pone "..." al final.
	private static String cortar(String texto, int largo) {
		if (texto == null)
			return "";
		if (texto.length() > largo) {
			return texto.substring(0, largo - 3) + "...";
		}
		return texto;
	}
}