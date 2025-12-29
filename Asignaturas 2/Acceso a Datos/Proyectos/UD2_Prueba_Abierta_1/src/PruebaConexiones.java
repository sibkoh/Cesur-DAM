import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexiones {

	public static void main(String[] args) {
		System.out.println("--- INICIO DE PRUEBAS DE CONEXIÓN UD2 ---");

		// 1. PRUEBA CON SGBD INDEPENDIENTE (MySQL via XAMPP)
		// Aseguramos de tener XAMPP arrancado y Apache/MySQL en verde.
		probarMySQL();

		System.out.println("-----------------------------------------");

		// 2. PRUEBA CON SGBD EMBEBIDO (SQLite)
		// Con esto crearemos un fichero 'mi_base_embebida.db' en la raíz del proyecto.
		probarSQLite();

		System.out.println("--- FIN DE LAS PRUEBAS ---");
	}

	private static void probarMySQL() {
		System.out.println("Intentando conectar a MySQL (Independiente)...");
		// Ajustamos usuario y contraseña

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String pass = "";

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			if (conn != null) {
				System.out.println("✅ ÉXITO: Conectado a MySQL.");
				System.out.println("   Info del Driver: " + conn.getMetaData().getDriverName());
				System.out.println("   Ventaja: Ideal para sistemas multiusuario concurrentes.");
			}
		} catch (SQLException e) {
			System.err.println("❌ ERROR en MySQL: " + e.getMessage());
			System.out.println("   (Revisa que XAMPP esté encendido)");
		}
	}

	private static void probarSQLite() {
		System.out.println("Intentando conectar a SQLite (Embebido)...");
		// La cadena de conexión es directa al fichero
		String url = "jdbc:sqlite:mi_base_embebida.db";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println("✅ ÉXITO: Conectado a SQLite.");
				System.out.println("   Info del Driver: " + conn.getMetaData().getDriverName());
				System.out.println("   Ventaja: Portabilidad total, la BD es un simple fichero.");
			}
		} catch (SQLException e) {
			System.err.println("❌ ERROR en SQLite: " + e.getMessage());
			System.out.println("   (Revisa que tengas el .jar de sqlite-jdbc en el Build Path)");
		}
	}
}