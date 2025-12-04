import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in); // Scanner para la entrada de datos
    private GestorArray gestorArray = new GestorArray(5); // Instancia del gestor de arrays
    private GestorArrayList gestorArrayList = new GestorArrayList(); // Instancia del gestor de ArrayList

    // Método para mostrar el menú principal
    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("Menú Principal:\n1. Usar Array\n2. Usar ArrayList\n3. Salir\n");
            opcion = pedirOpcion(); // Pedimos la opción de manera segura

            switch (opcion) {
                case 1:
                    gestorArray.mostrarMenuArray(); // Llamamos al menú de array
                    break;
                case 2:
                    gestorArrayList.mostrarMenuArrayList(); // Llamamos al menú de ArrayList
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.\n");
            }
        } while (opcion != 3); // Mientras el usuario no elija salir, repite el menú
    }

    // Método para pedir una opción de manera segura con manejo de excepciones
    private int pedirOpcion() {
        int opcion = -1;
        while (true) {
            try {
                System.out.print("Selecciona una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                if (opcion >= 1 && opcion <= 3) {
                    return opcion; // Si la opción es válida, la retornamos
                } else {
                    System.out.println("Opción inválida. Por favor, selecciona una opción entre 1 y 3.\n");
                }
            } catch (Exception e) {
                System.out.println("Error de entrada. Por favor, ingresa un número válido.\n");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }
}
