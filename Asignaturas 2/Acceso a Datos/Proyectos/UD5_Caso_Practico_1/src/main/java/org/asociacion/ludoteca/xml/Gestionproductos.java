package org.asociacion.ludoteca.xml;

import java.io.IOException;

import org.basex.api.client.LocalSession;
import org.basex.core.Context;

/**
 * Clase intermediaria (DAO) para la gestión del catálogo de juegos de mesa. *
 * NOTA ARQUITECTÓNICA: Tras enfrentarnos a bloqueos severos del Firewall de
 * Windows y conflictos con el protocolo IPv6 en el puerto 1984, decidimos
 * pivotar nuestra arquitectura hacia un modelo "Embebido" (Embedded) utilizando
 * LocalSession. Esto nos permite levantar el motor de BaseX directamente en la
 * memoria de Java, garantizando que la aplicación sea 100% portable y tolerante
 * a restricciones de red.
 */
public class Gestionproductos {

	// Usamos LocalSession en lugar de BaseXClient para aislar la BD de la red
	// externa.
	private LocalSession cliente;
	private Context contexto;

	// Constante con el nombre de nuestra base de datos lógica
	private final String NOMBRE_BD = "VentaProductos";

	/**
	 * CONSTRUCTOR: Inicializa el motor local y sincroniza la BD.
	 */
	public Gestionproductos() {
		try {
			// Creamos el contexto base que BaseX necesita para existir en memoria
			this.contexto = new Context();
			// Iniciamos la sesión local (sin pasar por sockets ni localhost)
			this.cliente = new LocalSession(contexto);

			System.out.println("-> [SISTEMA] Motor BaseX iniciado en Modo Local (Embebido).");

			// El comando CHECK abre la base de datos si existe, o la crea desde cero si no.
			cliente.execute("CHECK " + NOMBRE_BD);
			System.out.println("-> [SISTEMA] Base de datos '" + NOMBRE_BD + "' operativa.");
		} catch (Exception e) {
			System.err.println("-> [ERROR FATAL] Fallo al levantar el motor XML: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 1: Crear colecciones (Categorías lógicas). En BaseX las colecciones
	 * son rutas virtuales. Las creamos metiendo un archivo dummy.
	 */
	public void agregarColeccion(String nombreColeccion) {
		try {
			// Instrucción db:add nativa. Metemos un pequeño XML invisible para que BaseX
			// registre la carpeta.
			String xquery = "db:add('" + NOMBRE_BD + "', <coleccion nombre='" + nombreColeccion + "'/>, '"
					+ nombreColeccion + "/_metadata.xml')";
			cliente.execute("XQUERY " + xquery);
			System.out.println("-> Categoría '" + nombreColeccion + "' habilitada en el catálogo.");
		} catch (IOException e) {
			System.err.println("-> Error al generar la colección: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 2: Eliminar colecciones enteras.
	 */
	public void eliminarColeccion(String nombreColeccion) {
		try {
			// db:delete busca cualquier archivo que cuelgue de esta ruta y lo borra en
			// cascada
			String xquery = "db:delete('" + NOMBRE_BD + "', '" + nombreColeccion + "')";
			cliente.execute("XQUERY " + xquery);
			System.out.println("-> La categoría '" + nombreColeccion + "' ha sido purgada.");
		} catch (IOException e) {
			System.err.println("-> Error al borrar la colección: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 3: Añadir un nuevo juego (Documento XML). Cumple el requisito de
	 * insertar 1 producto por archivo XML.
	 */
	public void agregarProducto(String coleccion, String id, String nombre, String descripcion, double precio) {
		try {
			// Construimos la estructura jerárquica de nuestro juego en formato String
			String documentoXML = "<producto>" + "<id>" + id + "</id>" + "<nombre>" + nombre + "</nombre>"
					+ "<descripcion>" + descripcion + "</descripcion>" + "<precio>" + precio + "</precio>"
					+ "</producto>";

			// Le indicamos la colección como si fuera una carpeta (Ej:
			// JuegosExpertos/BGG-01.xml)
			String rutaVirtual = coleccion + "/" + id + ".xml";

			String xquery = "db:add('" + NOMBRE_BD + "', " + documentoXML + ", '" + rutaVirtual + "')";
			cliente.execute("XQUERY " + xquery);
			System.out.println("-> Juego registrado: [" + id + "] " + nombre);
		} catch (IOException e) {
			System.err.println("-> Error al inyectar el XML: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 4: Borrar un juego específico por su ID.
	 */
	public void eliminarProductoPorId(String idProducto) {
		try {
			// OJO: Usamos la función estándar W3C "collection()" en lugar de la propietaria
			// "db:open()".
			// Esto asegura que nuestra consulta XQuery sea estricta y compatible.
			// La instrucción 'delete node' pertenece a la extensión XQuery Update Facility.
			String xquery = "delete node collection('" + NOMBRE_BD + "')//producto[id='" + idProducto + "']";
			cliente.execute("XQUERY " + xquery);
			System.out.println("-> El juego [" + idProducto + "] ha sido descatalogado.");
		} catch (IOException e) {
			System.err.println("-> Error al borrar el nodo: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 5: Modificación atómica de datos. Requisito clave: Modificar el precio
	 * o descripción sin borrar y rehacer el archivo entero.
	 */
	public void modificarProducto(String idProducto, String nuevaDesc, double nuevoPrecio) {
		try {
			// 'replace value of node' viaja por el árbol XML hasta la hoja indicada y muta
			// solo su contenido interior.
			String xqDesc = "replace value of node collection('" + NOMBRE_BD + "')//producto[id='" + idProducto
					+ "']/descripcion with '" + nuevaDesc + "'";
			String xqPrecio = "replace value of node collection('" + NOMBRE_BD + "')//producto[id='" + idProducto
					+ "']/precio with '" + nuevoPrecio + "'";

			cliente.execute("XQUERY " + xqDesc);
			cliente.execute("XQUERY " + xqPrecio);
			System.out.println("-> Ficha actualizada para el juego [" + idProducto + "].");
		} catch (IOException e) {
			System.err.println("-> Error al mutar el XML: " + e.getMessage());
		}
	}

	/**
	 * CONSULTA 1: Búsqueda por nombre mediante FLWOR.
	 */
	public void buscarPorNombre(String texto) {
		try {
			// FOR: Iteramos sobre todos los documentos de la BD
			// WHERE: Usamos contains() transformando ambos textos a minúsculas para una
			// búsqueda flexible
			// RETURN: Devolvemos una cadena concatenada, no el XML en crudo.
			String xquery = "for $p in collection('" + NOMBRE_BD + "')//producto "
					+ "where contains(lower-case($p/nombre), lower-case('" + texto + "')) "
					+ "return concat('- ', $p/nombre, ' (', $p/precio, '€)')";

			String resultado = cliente.execute("XQUERY " + xquery);
			System.out.println(resultado.isEmpty() ? "No hay juegos con ese nombre." : resultado);
		} catch (IOException e) {
			System.err.println("-> Error en la consulta FLWOR: " + e.getMessage());
		}
	}

	/**
	 * CONSULTA 2: Búsqueda por rango de precios (Demostración de tipado en XML).
	 */
	public void buscarPorRangoDePrecios(double min, double max) {
		try {
			// En XML todo se guarda como texto. Por eso usamos la cláusula LET:
			// let $precio := xs:double($p/precio)
			// Esto fuerza al motor a tratar el nodo como un número real para que el 'order
			// by' funcione matemáticamente.
			String xquery = "for $p in collection('" + NOMBRE_BD + "')//producto "
					+ "let $precio := xs:double($p/precio) " + "where $precio >= " + min + " and $precio <= " + max
					+ " " + "order by $precio ascending " + "return concat($p/nombre, ' -> ', $precio, '€')";

			String resultado = cliente.execute("XQUERY " + xquery);
			System.out.println(resultado.isEmpty() ? "Inventario vacío en ese rango." : resultado);
		} catch (IOException e) {
			System.err.println("-> Error en filtrado numérico: " + e.getMessage());
		}
	}

	/**
	 * MÉTODO 6: Apagado seguro.
	 */
	public void cerrarConexion() {
		try {
			// Es vital liberar el archivo de la memoria RAM para que no se corrompa el .odb
			if (cliente != null)
				cliente.close();
			if (contexto != null)
				contexto.close();
			System.out.println("-> [SISTEMA] Motor cerrado de forma segura. ¡Hasta pronto!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}