
// import java.io.File;: Permite trabajar con archivos y directorios.
import java.io.File;

// import java.io.IOException;: Maneja errores al trabajar con archivos.
import java.io.IOException;

public class CreaEntorno {
	public static void main(String[] args) {

		// System.getProperty("user.dir"): Obtiene el directorio de ejecución del
		// programa.
		// File.separator: Usa el separador correcto según el sistema operativo (\ en
		// Windows, / en Linux/Mac).
		String directorio = System.getProperty("user.dir") + File.separator + "EjerciciosStreams";

		// Crear objeto File que representa el directorio
		File carpeta = new File(directorio);

		// Comprobar si el directorio existe
		if (!carpeta.exists()) {

			// mkdir(): Crea el directorio y devuelve true si tuvo éxito.
			if (carpeta.mkdir()) {
				System.out.println("Directorio creado exitosamente en: " + directorio);
			} else {
				System.out.println("Error al crear el directorio.");
				return;
			}
		} else {
			System.out.println("El directorio ya existía.");
		}

		// Se crean los nombres de los archivos que se van a crear.
		String archivo1 = "uno.java";
		String archivo2 = "dos.java";
		String archivo3 = "tres.java";

		// Se crea un objeto File con la ruta de cada archivo a crear.
		File archivoFile1 = new File(directorio + File.separator + archivo1);
		File archivoFile2 = new File(directorio + File.separator + archivo2);
		File archivoFile3 = new File(directorio + File.separator + archivo3);
		try {

			// createNewFile(): Crea el archivoFile1 si no existe.
			if (archivoFile1.createNewFile()) {
				System.out.println("archivoFile1 creado: " + archivoFile1.getName());
			} else {
				System.out.println("El archivoFile1 ya existía: " + archivoFile1.getName());
			}

			// Captura errores si ocurre un problema al crear archivoFile1s.
		} catch (IOException e) {
			System.out.println("Error al crear el archivoFile1: " + archivoFile1.getName());

			// Muestra el error en consola.
			e.printStackTrace();
		}

		try {

			// createNewFile(): Crea el archivoFile2 si no existe.
			if (archivoFile2.createNewFile()) {
				System.out.println("archivoFile2 creado: " + archivoFile2.getName());
			} else {
				System.out.println("El archivoFile2 ya existía: " + archivoFile2.getName());
			}

			// Captura errores si ocurre un problema al crear archivoFile2s.
		} catch (IOException e) {
			System.out.println("Error al crear el archivoFile2: " + archivoFile2.getName());

			// Muestra el error en consola.
			e.printStackTrace();
		}

		try {

			// createNewFile(): Crea el archivoFile3 si no existe.
			if (archivoFile3.createNewFile()) {
				System.out.println("archivoFile3 creado: " + archivoFile3.getName());
			} else {
				System.out.println("El archivoFile3 ya existía: " + archivoFile3.getName());
			}

			// Captura errores si ocurre un problema al crear archivoFile3s.
		} catch (IOException e) {
			System.out.println("Error al crear el archivoFile3: " + archivoFile3.getName());

			// Muestra el error en consola.
			e.printStackTrace();
		}

	}
}
