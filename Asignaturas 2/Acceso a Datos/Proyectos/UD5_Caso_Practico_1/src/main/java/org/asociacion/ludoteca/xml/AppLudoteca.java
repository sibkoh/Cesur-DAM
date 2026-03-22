package org.asociacion.ludoteca.xml;

/**
 * Clase de ejecución principal (Main). Mantenemos separada la lógica de
 * presentación y ejecución del acceso a datos para cumplir con los estándares
 * de diseño de software (Responsabilidad Única).
 */
public class AppLudoteca {

	public static void main(String[] args) {
		System.out.println("=================================================");
		System.out.println("=== ASOCIACIÓN JUEGOS: CATÁLOGO XML (BASEX) ===");
		System.out.println("=================================================\n");

		// Instanciamos nuestro DAO. Al estar en modo embebido, esto levanta BaseX por
		// detrás.
		Gestionproductos catalogo = new Gestionproductos();

		System.out.println("\n--- 1. PREPARACIÓN DE COLECCIONES ---");
		// Separamos nuestro catálogo en dos grandes categorías
		catalogo.agregarColeccion("JuegosExpertos");
		catalogo.agregarColeccion("JuegosFamiliares");

		System.out.println("\n--- 2. ENTRADA DE STOCK (INYECCIÓN XML) ---");
		// Cada llamada genera un documento .xml independiente dentro de la colección
		catalogo.agregarProducto("JuegosExpertos", "BGG-01", "Gloomhaven", "Juego de rol táctico, campaña de +100h",
				139.99);
		catalogo.agregarProducto("JuegosExpertos", "BGG-02", "Terraforming Mars",
				"Gestión corporativa en el planeta rojo", 54.95);
		catalogo.agregarProducto("JuegosFamiliares", "BGG-03", "Aventureros al Tren",
				"Rutas ferroviarias para todos los públicos", 45.00);
		catalogo.agregarProducto("JuegosFamiliares", "BGG-04", "Catan", "El clásico de la recolección y el comercio",
				40.00);

		System.out.println("\n--- 3. MODIFICACIÓN DE DATOS (UPDATE FACILITY) ---");
		// Simulamos una subida de precio y una mejora en la descripción sin destruir la
		// estructura base del XML
		catalogo.modificarProducto("BGG-04", "Edición revisada (10º Aniversario). Construye y negocia.", 49.99);

		System.out.println("\n--- 4. BÚSQUEDAS AVANZADAS (XQUERY) ---");
		System.out.println(">> Filtrando juegos que contengan 'mars':");
		catalogo.buscarPorNombre("mars");

		System.out.println("\n>> Buscando regalos entre 40.00€ y 60.00€:");
		catalogo.buscarPorRangoDePrecios(40.00, 60.00);

		System.out.println("\n--- 5. LIMPIEZA DEL CATÁLOGO ---");
		// Damos de baja un juego porque se nos ha agotado el stock (Borrado de un nodo
		// específico)
		catalogo.eliminarProductoPorId("BGG-01");

		System.out.println("\n--- 6. APAGADO ---");
		catalogo.cerrarConexion();
	}
}