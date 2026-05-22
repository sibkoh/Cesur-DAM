package com.empresa.rrhh.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MotorAsistenciaDB {

	// Credenciales de conexión. En un entorno profesional, esto nunca se pone en
	// texto plano,
	// se leería de un archivo de configuración seguro o variables de entorno.
	private final String URL = "jdbc:mariadb://localhost:3306/control_personal";
	private final String USER = "root";
	private final String PASSWORD = "";

	/**
	 * Método privado y centralizado para obtener la conexión. Si algún día
	 * cambiamos de base de datos, solo tocamos esta línea.
	 */
	private Connection conectar() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * Registra la hora de entrada de un empleado.
	 * 
	 * @param nombre El nombre del empleado que está fichando.
	 * @return true si se guardó con éxito, false si hubo error o si ya había
	 *         entrado hoy.
	 */
	public boolean registrarEntrada(String nombre) {
		// Regla de negocio: Un trabajador no puede entrar dos veces el mismo día sin
		// salir.
		if (yaTieneEntradaHoy(nombre)) {
			System.out.println("LOG: Intento de doble entrada rechazado para: " + nombre);
			return false;
		}

		// PREVENCIÓN DE INYECCIÓN SQL:
		// Usamos '?' en lugar de concatenar el nombre directamente en el String.
		// PreparedStatement se encarga de 'limpiar' el texto para que nadie pueda
		// borrar la tabla.
		String sql = "INSERT INTO fichajes (nombre_empleado, hora_entrada, fecha) VALUES (?, ?, ?)";

		// El bloque 'try-with-resources' (el try con paréntesis) asegura que la
		// conexión
		// se cierre automáticamente al terminar, evitando fugas de memoria en el
		// servidor.
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Sustituimos las interrogaciones por los datos reales
			pstmt.setString(1, nombre);
			// Insertamos la hora y fecha exacta del sistema operativo en este milisegundo
			pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setDate(3, Date.valueOf(LocalDate.now()));

			// executeUpdate() devuelve el número de filas afectadas. Si es > 0, se insertó
			// bien.
			int filasInsertadas = pstmt.executeUpdate();
			return filasInsertadas > 0;

		} catch (SQLException e) {
			System.err.println("Error crítico al registrar entrada: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Registra la salida de un empleado actualizando su fila de hoy.
	 */
	public boolean registrarSalida(String nombre) {
		// Reglas de negocio: Para salir, tienes que haber entrado hoy, y no haber
		// salido ya.
		if (!yaTieneEntradaHoy(nombre))
			return false;
		if (yaTieneSalidaHoy(nombre))
			return false;

		// Actualizamos (UPDATE) la fila que coincida con el nombre y la fecha de hoy
		String sql = "UPDATE fichajes SET hora_salida = ? WHERE nombre_empleado = ? AND fecha = ?";

		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(2, nombre);
			pstmt.setDate(3, Date.valueOf(LocalDate.now()));

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}

	// ===================================================================================
	// MÉTODOS AUXILIARES DE VALIDACIÓN
	// Estos métodos ayudan a verificar el estado del empleado sin modificar datos.
	// ===================================================================================

	public boolean yaTieneEntradaHoy(String nombre) {
		String sql = "SELECT id FROM fichajes WHERE nombre_empleado = ? AND fecha = ?";
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombre);
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));

			// executeQuery() se usa para consultas SELECT. Devuelve un ResultSet (un
			// cursor).
			ResultSet rs = pstmt.executeQuery();
			// Si rs.next() es true, significa que encontró al menos una fila (el empleado
			// ya entró).
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean yaTieneSalidaHoy(String nombre) {
		// Buscamos una fila de hoy donde la hora_salida NO sea nula
		String sql = "SELECT id FROM fichajes WHERE nombre_empleado = ? AND fecha = ? AND hora_salida IS NOT NULL";
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombre);
			pstmt.setDate(2, Date.valueOf(LocalDate.now()));
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * MÉTODO EXCLUSIVO PARA PRUEBAS (TESTING) Borra todos los registros de la
	 * tabla. Se usa para garantizar que cada test empiece con una base de datos
	 * totalmente limpia.
	 */
	public void limpiarTablaPruebas() {
		try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("TRUNCATE TABLE fichajes");
		} catch (SQLException e) {
			System.err.println("No se pudo limpiar la tabla de pruebas.");
		}
	}
}