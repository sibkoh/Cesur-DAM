import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tres {
    public static void main(String[] args) {
        // Definir la ruta del archivo datos.txt de manera relativa
        String rutaArchivo = System.getProperty("user.dir") + File.separator + "datos.txt";

        // Crear objeto File
        File archivo = new File(rutaArchivo);

        // Comprobar si el archivo existe
        if (!archivo.exists()) {
            System.out.println("El archivo datos.txt no existe. Asegúrate de haberlo creado.");
            return;
        }

        // Leer el archivo y mostrar el contenido
        try (Scanner lector = new Scanner(archivo)) {
            System.out.println("Contenido del directorio telefónico:");
            while (lector.hasNextLine()) {
                System.out.println(lector.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        }
    }
}
