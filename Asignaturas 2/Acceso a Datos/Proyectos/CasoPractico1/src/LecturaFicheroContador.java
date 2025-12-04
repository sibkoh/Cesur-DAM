import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LecturaFicheroContador {

    public static void leerFicheroConContador(String ruta) {
        int contadorLineas = 0;
        // try-with-resources para cerrar automáticamente FileReader/BufferedReader
        try (FileReader fr = new FileReader(ruta);
             BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                contadorLineas++;
                // mostramos la línea y el número de caracteres
                System.out.println(linea + "-->" + linea.length());
            }
            System.out.println("el número de líneas es:" + contadorLineas);

        } catch (FileNotFoundException e) {
            System.err.println("Error: fichero no encontrado -> " + ruta);
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el fichero: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Podemos recibir la ruta por args o usar una ruta por defecto
        String ruta = "c:/prueba.txt";
        System.out.println(ruta);
        if (args.length > 0) {
            ruta = args[0];
        }
        leerFicheroConContador(ruta);
    }
}
