import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dos {
    public static void main(String[] args) {
        // Definir ruta del archivo en la carpeta relativa
        String directorio = System.getProperty("user.dir") + File.separator + "EjerciciosStreams";
        String rutaArchivo = directorio + File.separator + "dos.java";

        // Crear Scanner para entrada de datos
        Scanner scanner = new Scanner(System.in);

        // Pedir base y altura al usuario
        System.out.print("Introduce la base del triángulo: ");
        double base = scanner.nextDouble();

        System.out.print("Introduce la altura del triángulo: ");
        double altura = scanner.nextDouble();

        // Calcular el área del triángulo
        double area = (base * altura) / 2;
        System.out.println("El área del triángulo es: " + area);

        // Escribir el resultado en el archivo
        try (FileWriter escritor = new FileWriter(rutaArchivo)) {
            escritor.write("Base: " + base + "\n");
            escritor.write("Altura: " + altura + "\n");
            escritor.write("Área: " + area + "\n");
            System.out.println("Resultado guardado en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
            e.printStackTrace();
        }

        // Cerramos el objeto scanner
        scanner.close();
    }
}
