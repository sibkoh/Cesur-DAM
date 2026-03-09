package org.asociacion.compras.ui;

import java.util.Date; // IMPORTANTE: Modificado a java.util.Date
import java.util.List;
import java.util.Scanner;

import org.asociacion.compras.config.GestorConexionObjectDB;
import org.asociacion.compras.dao.RepositorioClientesCrud;
import org.asociacion.compras.modelo.ClienteEditorial;

/**
 * Clase principal (Main) que arranca la aplicación, muestra el menú y recolecta
 * los datos de entrada del usuario comercial.
 */
public class AppGestorVisitas {

	private static RepositorioClientesCrud crud = new RepositorioClientesCrud();
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("=================================================");
		System.out.println("=== ASOCIACIÓN JUEGOS: HISTÓRICO VISITAS B2B ===");
		System.out.println("=================================================");

		boolean salir = false;

		while (!salir) {
			mostrarMenu();
			try {
				// Usamos nextLine() y parseamos para evitar el bug del salto de línea del
				// Scanner
				int opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 1:
					crearNuevoCliente();
					break;
				case 2:
					listarClientes();
					break;
				case 3:
					registrarVisitaInteractiva();
					break;
				case 4:
					borrarClienteInteractivo();
					break;
				case 5:
					salir = true;
					// Importantísimo cerrar la factoría de JPA antes de apagar la JVM
					GestorConexionObjectDB.cerrarConexion();
					System.out.println("Cerrando la base de datos de ObjectDB. ¡Hasta pronto!");
					break;
				default:
					System.out.println("-> Opción no válida. Introduce un número del 1 al 5.");
				}
			} catch (NumberFormatException e) {
				System.out.println("-> ERROR: Por favor, introduce un número válido, no texto.");
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n--- MENÚ PRINCIPAL ---");
		System.out.println("1. Alta de nuevo representante (Cliente)");
		System.out.println("2. Ver listado completo y total de visitas");
		System.out.println("3. Añadir fecha de visita a un representante");
		System.out.println("4. Dar de baja a un representante");
		System.out.println("5. Guardar y Salir");
		System.out.print("Elige una acción >> ");
	}

	private static void crearNuevoCliente() {
		System.out.println("\n-- NUEVO REGISTRO --");
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Primer Apellido: ");
		String ape1 = scanner.nextLine();
		System.out.print("Segundo Apellido: ");
		String ape2 = scanner.nextLine();
		System.out.print("Tu nombre (Comercial Principal): ");
		String comercial = scanner.nextLine();
		System.out.print("ID de la Editorial (Ej: DEVIR-01): ");
		String idEmpresa = scanner.nextLine();

		// Instanciamos el objeto Transient
		ClienteEditorial nuevo = new ClienteEditorial(nombre, ape1, ape2, comercial, idEmpresa);
		// Delegamos en el DAO la persistencia
		crud.guardarCliente(nuevo);
		System.out.println("-> Representante " + nombre + " añadido a la base de datos.");
	}

	private static void listarClientes() {
		System.out.println("\n-- LISTADO DE REPRESENTANTES Y VISITAS --");
		List<ClienteEditorial> lista = crud.listarTodos();
		if (lista.isEmpty()) {
			System.out.println("La base de datos está vacía.");
			return;
		}
		for (ClienteEditorial c : lista) {
			// Se invoca automáticamente el método toString() sobrescrito en el modelo
			System.out.println(c);
		}
	}

	private static void registrarVisitaInteractiva() {
		System.out.println("\n-- REGISTRAR NUEVA VISITA --");
		listarClientes(); // Mostramos los clientes para que el usuario vea los IDs
		System.out.print("\nIntroduce el ID numérico del representante que has visitado: ");
		try {
			Long id = Long.parseLong(scanner.nextLine());
			// CÁMBIO APLICADO: Pasamos un objeto Date tradicional con la fecha y hora
			// actual
			crud.añadirVisitaACliente(id, new Date());
		} catch (NumberFormatException e) {
			System.out.println("-> ERROR: El ID debe ser un número entero.");
		}
	}

	private static void borrarClienteInteractivo() {
		System.out.println("\n-- DAR DE BAJA --");
		System.out.print("Introduce el ID del representante a eliminar: ");
		try {
			Long id = Long.parseLong(scanner.nextLine());
			crud.eliminarCliente(id);
		} catch (NumberFormatException e) {
			System.out.println("-> ERROR: El ID debe ser un número entero.");
		}
	}
}