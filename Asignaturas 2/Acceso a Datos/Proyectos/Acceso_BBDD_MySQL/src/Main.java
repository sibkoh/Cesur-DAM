// Clase principal del programa
public class Main {

	// Método main: punto de entrada del programa
	public static void main(String[] args) {

		// Creamos un objeto de la clase Acceso, que se encargará
		// de gestionar la conexión con la base de datos y las consultas
		Acceso acceso = new Acceso();

		// Llamamos al método Conectar() del objeto acceso
		// Este método establecerá la conexión con la base de datos MySQL
		acceso.Conectar();

		// Definimos una consulta SQL simple para obtener todos los registros de la
		// tabla "Alumnos"
		String consulta = "SELECT * FROM Alumnos";

		// Llamamos al método RealizarConsulta() del objeto acceso,
		// pasando la consulta SQL como parámetro.
		// Este método ejecutará la consulta y, presumiblemente, mostrará los resultados
		acceso.RealizarConsulta(consulta);

		// Al finalizar, cerrar la conexión y recursos
		acceso.Cerrar();
	}
}
