import java.util.Scanner;

public class GestorArray {

    private Estudiante[] estudiantes;
    private int contador;

    public GestorArray(int tamaño) {
        estudiantes = new Estudiante[tamaño];
        contador = 0;
    }

    // Método para mostrar el menú de opciones relacionadas con el array
    public void mostrarMenuArray() {
        int opcion;
        do {
            System.out.println("\nMenú Array:");
            System.out.println("1. Agregar un estudiante al array");
            System.out.println("2. Ver estudiantes en el array");
            System.out.println("3. Volver al menú principal\n");
            opcion = pedirOpcionArray(); // Pedimos la opción de manera segura

            switch (opcion) {
                case 1: agregarEstudiante(); break;
                case 2: verEstudiantes(); break;
                case 3: System.out.println("Volviendo al menú principal...\n"); break;
                default: System.out.println("Opción inválida.\n");
            }
        } while (opcion != 3);
    }

    // Método para pedir una opción en el submenú Array de manera segura
    private int pedirOpcionArray() {
        int opcion = -1;
        while (true) {
            try {
                System.out.print("Selecciona una opción: ");
                opcion = new Scanner(System.in).nextInt();
                if (opcion >= 1 && opcion <= 3) {
                    return opcion;
                } else {
                    System.out.println("Opción inválida. Por favor, selecciona una opción entre 1 y 3.\n");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada. Por favor, ingresa un número válido.\n");
            }
        }
    }

    // Método para agregar un estudiante al array
    private void agregarEstudiante() {
        if (contador < estudiantes.length) {
            try {
                System.out.print("Nombre del estudiante: ");
                String nombre = new Scanner(System.in).nextLine();

                System.out.print("Edad del estudiante: ");
                int edad = new Scanner(System.in).nextInt();

                System.out.print("Nota final del estudiante: ");
                double nota = new Scanner(System.in).nextDouble();

                Estudiante estudiante = new Estudiante(nombre, edad, nota);
                estudiantes[contador] = estudiante;
                contador++;
                System.out.println("Estudiante agregado correctamente.\n");
            } catch (Exception e) {
                System.out.println("Error al ingresar los datos. Inténtalo de nuevo.\n");
            }
        } else {
            System.out.println("No se pueden agregar más estudiantes, el array está lleno.\n");
        }
    }

    // Método para ver todos los estudiantes en el array
    private void verEstudiantes() {
        if (contador == 0) {
            System.out.println("No hay estudiantes en el array.\n");
        } else {
            for (int i = 0; i < contador; i++) {
                System.out.println(estudiantes[i]);
            }
        }
    }
}
