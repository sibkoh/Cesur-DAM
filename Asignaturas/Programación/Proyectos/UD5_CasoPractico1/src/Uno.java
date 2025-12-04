import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Uno {
    public static void main(String[] args) {
        // Definir la ruta del archivo con relación a la carpeta del proyecto
        String directorio = System.getProperty("user.dir") + File.separator + "EjerciciosStreams";
        String rutaArchivo = directorio + File.separator + "uno.java";

        // Crear objeto File para representar el archivo
        File archivo = new File(rutaArchivo);

        // Verificar si el archivo existe antes de escribir
        if (!archivo.exists()) {
            System.out.println("El archivo no existe. Asegúrate de haber ejecutado CreaEntorno.java.");
            return;
        }

                
        // FileWriter permite escribir en el archivo.
        try (FileWriter escritor = new FileWriter(archivo)) {
        
        	// Escribe los números del 0 al 10 en el archivo
        	for (int i = 0; i <= 10; i++) {
                escritor.write(i + "\n");
            }
            System.out.println("Números del 0 al 10 escritos en " + archivo.getName());
            
            // Si excepción ocurre, ejecuta el siguiente código
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
            e.printStackTrace();
        }
    }
}
