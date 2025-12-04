import java.util.Scanner;

public class Ejercicio3 {

	public static void main(String[] args) {

		// Creamos la variable para almacenar el número a recibir por consola,
		// usamos long para evitar que de error si el número es demasiado largo

		long numOriginal = 0;
		long numContador = 0;

		// Creamos un nuevo objeto tipo Scanner, específico para leer tokens desde el
		// buffer de teclado

		Scanner scanner = new Scanner(System.in);

		// Creamos un bucle que se repetirá hasta que la condición del input de usuario
		// sea la correcta

		do {

			// Imprimimos por consola el mensaje para pedir los datos

			System.out.println("Introduce un número entero positivo por favor: ");

			// Almacenamos el resultado del input en la variable, el metodo nextLong() solo
			// lee el número y no el salto de línea
			// no da error, si encuentra espacios antes del numero, pero sí ocurre, si input
			// no es un número entero

			numOriginal = scanner.nextLong();

			if (numOriginal <= 0) {

				System.out.println("Error. Sólo números positivos ");

			} else {

				System.out.println("Los siguientes 20 números son: ");
			}

		} while (numOriginal <= 0);

		// Asignamos el número original a otra variable que iremos modificando para no
		// perder el valor original

		numContador = numOriginal;

		// Creamos un bucle for porque sabemos el número de iteraciones (20), para
		// aumentar el numero 20
		// veces e imprimirlo. Usamos byte en el iterador para ahorrar memoria

		for (byte i = 0; i < 20; ++i) {

			// abreviatura de la expresión num = num + 1; Aumentamos el contador antes de
			// imprimir
			// porque nos piden los siguientes 20 números, el primero no puede ser el
			// original

			numContador++;

			// imprime la linea que contenga el parámetro y el salto de línea

			System.out.println(numContador);

		}

		// Cerramos el objeto Scanner para evitar errores posteriores

		scanner.close();

	}

}
