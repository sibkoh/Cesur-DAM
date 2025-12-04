import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Acceso {
	private Connection conexion; // Objeto para la conexión con la base de datos
	private Statement st; // Objeto para ejecutar consultas SQL
	private ResultSet rs; // Objeto que almacena resultados de SELECT

	// Constructor inicializa los objetos a null
	public Acceso() {
		conexion = null;
		st = null;
		rs = null;
	}

	// Método para establecer conexión con la base de datos
	public void Conectar() {
		try {
			// Cargar driver JDBC de MySQL
			// Esto permite que Java sepa cómo comunicarse con MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// URL de conexión: localhost, puerto 3306, base de datos "aad"
			// serverTimezone=UTC evita problemas de zona horaria
			// useSSL=false desactiva SSL para pruebas locales
			String url = "jdbc:mysql://localhost:3306/aad?serverTimezone=UTC&useSSL=false";
			String usuario = "root"; // Usuario de la BD
			String contraseña = ""; // Contraseña del usuario

			// Crear la conexión a la base de datos
			conexion = DriverManager.getConnection(url, usuario, contraseña);

			// Crear Statement para ejecutar consultas
			// TYPE_SCROLL_INSENSITIVE permite mover el cursor por el ResultSet
			// CONCUR_READ_ONLY indica que solo leeremos, no modificaremos datos
			st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			System.out.println("Conexión establecida correctamente.");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver JDBC no encontrado: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error al conectar con la BD: " + e.getMessage());
		}
	}

	// Método para ejecutar un SELECT y mostrar resultados + metadatos
	public void RealizarConsulta(String consulta) {
		if (conexion == null) {
			System.err.println("No está conectada la BD. Llama a conectar() primero.");
			return;
		}

		try {
			// Ejecutar la consulta SQL
			rs = st.executeQuery(consulta);

			// --- Mostrar metadatos ---
			ResultSetMetaData rsMeta = rs.getMetaData(); // Permite obtener info de columnas
			int numeroColumnas = rsMeta.getColumnCount(); // Número total de columnas
			System.out.println("Número de columnas: " + numeroColumnas);

			// Nombre de la tabla y catálogo (puede devolver vacío según driver)
			String nombreTabla = rsMeta.getTableName(1); // Intenta tomar el nombre de la primera columna
			String catalogo = conexion.getCatalog(); // Nombre de la base de datos activa
			System.out.println("Nombre de tabla (si está disponible): "
					+ (nombreTabla.isEmpty() ? "(no disponible)" : nombreTabla));
			System.out.println("Nombre del esquema o catálogo: " + catalogo);
			System.out.println("\n\nDatos de la tabla alumnos con columnas");
			System.out.println("--------------------------------------");

			// --- Mostrar nombres de columnas alineados ---
			for (int i = 1; i <= numeroColumnas; i++) {
				if (i == 1) { // ID
					System.out.print(String.format("%-4s", rsMeta.getColumnName(i))); // Ancho 4, alineado a la
																						// izquierda
				} else {
					System.out.print(String.format("%-20s", rsMeta.getColumnName(i))); // Ancho 20 para otras columnas
				}
			}
			System.out.println();
			// System.out.println("-".repeat(4 + 20 * (numeroColumnas - 1))); // Línea
			// separadora

			// --- Mostrar filas de resultados alineadas ---
			while (rs.next()) {
				// Recorrer cada columna de la fila actual
				for (int i = 1; i <= numeroColumnas; i++) {
					Object valor = rs.getObject(i); // Obtener valor genérico (int, String, etc.)
					if (i == 1) {
						// ID: ancho 4
						System.out.print(String.format("%-4s", valor));
					} else {
						// Otras columnas: ancho 20
						System.out.print(String.format("%-20s", valor));
					}
				}
				System.out.println(); // Nueva línea para la siguiente fila
			}

			// --- Contar filas devueltas (opcional) ---
			int filas = 0;
			if (rs != null) {
				rs.last(); // Mover cursor a la última fila
				filas = rs.getRow(); // Obtener número de fila actual = total de filas
				rs.beforeFirst(); // Volver al inicio para lectura si se necesitara
				System.out.println("Número de filas devueltas: " + filas);
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		} finally {
			// Cerrar ResultSet de esta consulta para liberar recursos
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				// Ignorar excepción en cierre
			}
		}
	}

	// Método para cerrar todos los recursos al finalizar
	public void Cerrar() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
			if (conexion != null) {
				conexion.close();
				conexion = null;
			}
			System.out.println("Conexión cerrada.");
		} catch (SQLException e) {
			System.err.println("Error al cerrar recursos: " + e.getMessage());
		}
	}
}
