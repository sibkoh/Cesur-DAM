import java.util.Scanner;
import java.util.ArrayList;

//Metodo Main

public class Main {
	public static void main(String[] args) {
		// Crear una instancia de la clase Menu
		Menu menu = new Menu();

		// Llamar al método mostrarMenuPrincipal usando la instancia del objeto
		menu.mostrarMenuPrincipal();
	}

	
	
	
	// Clase Estudiante

	static class Estudiante {

		private String nombre;
		private int edad;
		private double notaMedia;

		// Constructor
		public Estudiante(String nombre, int edad, double notaMedia) {
			this.nombre = nombre;
			this.edad = edad;
			this.notaMedia = notaMedia;
		}

		// Getter para el nombre
		public String getNombre() {
			return nombre;
		}

		// Getter para la edad
		public int getEdad() {
			return edad;
		}

		// Getter para la nota final
		public double getNotaMedia() {
			return notaMedia;
		}

		// Método toString para representar el estudiante de manera legible
		@Override
		public String toString() {
			return "Nombre: " + nombre + ", Edad: " + edad + ", Nota final: " + notaMedia;
		}
	}

	
	
	
// Clase GestorArray

	static class GestorArray {

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
				case 1:
					agregarEstudiante();
					break;
				case 2:
					verEstudiantes();
					break;
				case 3:
					System.out.println("Volviendo al menú principal...\n");
					break;
				default:
					System.out.println("Opción inválida.\n");
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
	
	
	

// Clase GestorArrayList

	static class GestorArrayList {

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
				case 1:
					agregarEstudiante();
					break;
				case 2:
					verEstudiantes();
					break;
				case 3:
					eliminarEstudiante();
					break;
				case 4:
					buscarEstudiante();
					break;
				case 5:
					System.out.println("Volviendo al menú principal...\n");
					break;
				default:
					System.out.println("Opción inválida.\n");
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
	
	
	

// Clase Menu

	static class Menu {

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
}
