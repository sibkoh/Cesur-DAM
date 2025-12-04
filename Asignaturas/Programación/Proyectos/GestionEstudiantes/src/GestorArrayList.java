import java.util.ArrayList;
import java.util.Scanner;

public class GestorArrayList {

    private ArrayList<Estudiante> estudiantes;
    private Scanner scanner; // Scanner único para reutilizar

    public GestorArrayList() {
        estudiantes = new ArrayList<>();
        scanner = new Scanner(System.in); // Inicializamos el scanner
    }

    // Método para mostrar el menú del ArrayList
    public void mostrarMenuArrayList() {
        int opcion;
        do {
            System.out.println("\nMenú ArrayList:");
            System.out.println("1. Agregar un estudiante al ArrayList");
            System.out.println("2. Ver estudiantes en el ArrayList");
            System.out.println("3. Eliminar un estudiante del ArrayList");
            System.out.println("4. Buscar un estudiante por nombre en el ArrayList");
            System.out.println("5. Volver al menú principal\n");
            opcion = pedirOpcionArrayList(); // Pedimos la opción de manera segura

            switch (opcion) {
                case 1: agregarEstudiante(); break;
                case 2: verEstudiantes(); break;
                case 3: eliminarEstudiante(); break;
                case 4: buscarEstudiante(); break;
                case 5: System.out.println("Volviendo al menú principal...\n"); break;
                default: System.out.println("Opción inválida.\n");
            }
        } while (opcion != 5);
    }

    // Método para pedir una opción en el submenú ArrayList de manera segura
    private int pedirOpcionArrayList() {
        int opcion = -1;
        while (true) {
            try {
                System.out.print("Selecciona una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                if (opcion >= 1 && opcion <= 5) {
                    return opcion;
                } else {
                    System.out.println("Opción inválida. Por favor, selecciona una opción entre 1 y 5.\n");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada. Por favor, ingresa un número válido.\n");
                scanner.nextLine(); // Limpiar el buffer del scanner para evitar un bucle infinito
            }
        }
    }

    // Método para agregar un estudiante al ArrayList
    private void agregarEstudiante() {
        try {
            System.out.print("Nombre del estudiante: ");
            String nombre = scanner.nextLine();

            System.out.print("Edad del estudiante: ");
            int edad = scanner.nextInt();

            System.out.print("Nota final del estudiante: ");
            double nota = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer

            Estudiante estudiante = new Estudiante(nombre, edad, nota);
            estudiantes.add(estudiante);
            System.out.println("Estudiante agregado correctamente.\n");
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos. Inténtalo de nuevo.\n");
            scanner.nextLine(); // Limpiar buffer para evitar un bucle infinito
        }
    }

    // Método para ver todos los estudiantes en el ArrayList
    private void verEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes en el ArrayList.\n");
        } else {
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante);
            }
        }
    }

    // Método para eliminar un estudiante por nombre
    private void eliminarEstudiante() {
        System.out.print("Introduce el nombre del estudiante a eliminar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getNombre().equalsIgnoreCase(nombre)) { // Comparamos solo el nombre
                estudiantes.remove(i);
                System.out.println("Estudiante eliminado correctamente.\n");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Estudiante no encontrado.\n");
        }
    }

    // Método para buscar un estudiante por nombre
    private void buscarEstudiante() {
        System.out.print("Introduce el nombre del estudiante a buscar: ");
        String nombre = scanner.nextLine();
        boolean encontrado = false;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) { // Comparamos solo el nombre
                System.out.println("\nEstudiante encontrado: " + estudiante);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Estudiante no encontrado.\n");
        }
    }
}
                