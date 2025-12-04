import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Cuatro {
    public static void main(String[] args) {
        // Definir rutas relativas
        String rutaDatos = System.getProperty("user.dir") + File.separator + "datos.txt";
        String rutaDatosAleatorio = System.getProperty("user.dir") + File.separator + "datosAleatorio";

        // Verificar si el archivo datos.txt existe
        File archivoDatos = new File(rutaDatos);
        if (!archivoDatos.exists()) {
            System.out.println("El archivo datos.txt no existe. Asegúrate de haberlo creado.");
            return;
        }

        // Abrir el buffer de fluyo de datos de lectura, creando un objeto buffer de lectura que leerá el archivo datos.txt
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoDatos));
        		
        		// Crear el archivo de acceso aleatorio
        	     RandomAccessFile archivoAleatorio = new RandomAccessFile("datosAleatorio", "rw")) {

        	    String linea;
        	    while ((linea = lector.readLine()) != null) {  // Lee línea por línea
        	        archivoAleatorio.writeUTF(linea + "\n");   // Escribe en el archivo de acceso aleatorio. WriteUTF es más compatible con codificacion UTF8 que writebytes.
        	    }

        	    System.out.println("Archivo de acceso aleatorio generado correctamente.");

        	} catch (IOException e) {
        	    System.err.println("Error al leer/escribir archivos: " + e.getMessage());
        	}

    }
}
