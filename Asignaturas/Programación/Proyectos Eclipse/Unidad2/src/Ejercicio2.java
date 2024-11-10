
public class Ejercicio2 {

	public static void main(String[] args) {

		// Creamos la variable que usaremos como contador hacia atrás por eso la
		// inicializamos en 10

		byte num = 10;

		// Creamos una variable bool, para usarla de flag y controle el bucle while

		Boolean seguirIterando = true;

		System.out.print("Cuenta regresiva del 10 al 1: ");

		// Bucle while que terminará cuando la variable seguirIterando la cambiemos a
		// false

		while (seguirIterando == true) {

			// Condición límite hasta la cual imprimiremos por consola e iremos restando 1 a
			// la variable num

			if (num > 0) {

				System.out.print(num + " ");

				// Abreviatura de la expresión num = num - 1

				num--;

				// Cuando no se cumpla la condición anterior, dispararemos el final del bucle
				// poniendo la condición a false

			} else {

				seguirIterando = false;
			}

		}

	}

}
