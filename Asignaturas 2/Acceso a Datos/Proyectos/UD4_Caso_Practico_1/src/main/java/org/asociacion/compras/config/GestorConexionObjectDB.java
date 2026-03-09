package org.asociacion.compras.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase encargada de gestionar el ciclo de vida de la conexión con ObjectDB.
 * Utilizamos un patrón Singleton para garantizar que la factoría de conexiones
 * se instancie una única vez durante toda la ejecución del programa.
 */
public class GestorConexionObjectDB {

	// Almacenamos la factoría de forma estática.
	private static EntityManagerFactory factoria;

	// Constructor privado para evitar que otros programadores hagan "new
	// GestorConexionObjectDB()"
	private GestorConexionObjectDB() {
	}

	/**
	 * Devuelve un manejador de entidades (EntityManager) listo para operar. Si la
	 * factoría no existe, la crea leyendo el persistence.xml ("EditorialesPU").
	 */
	public static EntityManager obtenerManejador() {
		if (factoria == null || !factoria.isOpen()) {
			// "EditorialesPU" debe coincidir EXACTAMENTE con el <persistence-unit
			// name="..."> del persistence.xml
			factoria = Persistence.createEntityManagerFactory("EditorialesPU");
		}
		// El EntityManager es el objeto que realmente hará los persist(), find(),
		// merge(), etc.
		return factoria.createEntityManager();
	}

	/**
	 * Método para liberar recursos al cerrar la aplicación de forma segura.
	 */
	public static void cerrarConexion() {
		if (factoria != null && factoria.isOpen()) {
			factoria.close();
		}
	}
}