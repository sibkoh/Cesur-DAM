import java.util.Scanner;

public class Ejercicio1 {

	public static void main(String[] args) {

		// Creamos las variables para almacenar los tres números a recibir por consola,
		// usamos long para evitar que de error si el número es demasiado largo

		long num1 = 0;
		long num2 = 0;
		long num3 = 0;

		// Variables donde almacenaremos los números ordenados

		long numOrdenado1 = 0;
		long numOrdenado2 = 0;
		long numOrdenado3 = 0;

		// Variable auxiliar para poder intercambiar valores

		long numAux = 0;

		// Variable de texto donde almacenaremos el tipo de orden introducido por el
		// usuario UP o DOWN

		String orden = "";

		// Variable Booleana para usar como condición, si el input del usuario para el
		// orden es correcto

		boolean opcionCorrecta = false;

		// Creamos un nuevo objeto tipo Scanner, específico para leer tokens desde el
		// buffer de teclado

		Scanner scanner = new Scanner(System.in);

		// Creamos un bucle for porque sabemos el número de iteraciones (3), para
		// repetir la
		// peticion de numero tres veces. Usamos byte en el iterador para ahorrar
		// memoria

		for (byte i = 0; i < 3; ++i) {

			// Imprimimos por consola el mensaje para pedir los datos

			System.out.println("Introduce un número entero: ");

			// Usamos Switch para separar cada iteracion del input en una variable diferente

			switch (i) {

			case 0:

				// Almacenamos el resultado del input en la variable, el metodo nextLong() solo
				// lee el número y no el salto de línea
				// no da error, si encuentra espacios antes del numero, pero sí ocurre, si input
				// no es un número entero

				num1 = scanner.nextLong();

				// Usamos el metodo nextln() para consumir el buffer con el salto de línea
				// incluido y así evitar errores en futuros inputs.

				scanner.nextLine();

				// Interrumpimos el switch para que no siga ejecutando el resto de los casos

				break;

			case 1:

				num2 = scanner.nextLong();

				scanner.nextLine();

				break;

			case 2:

				num3 = scanner.nextLong();

				scanner.nextLine();

				break;
			}

		}

		// Encerramos el input del input de orden en un bucle que se repetirá hasta que
		// el input sea una de las
		// opciones validas

		do {

			// Imprimimos por consola el mensaje para decidir las opciones de orden

			System.out.println("Introduce UP para orden ascendente o DOWN para orden descendente: ");

			// Almacenamos toda la línea hasta que encontremos un \n

			orden = scanner.nextLine();

			// Quitamos los espacios delante y dentras a la cadena para evitar errores. lo
			// asignamos a sí mismo
			// porque en Java los objetos son inmutables , no se pueden modificar su
			// contenido a no ser que haya una nueva asignación

			orden = orden.trim();

			// Convertimos el string a mayúsculas por si el usuario comete algún error de
			// entrada

			orden = orden.toUpperCase();

			// Comprobamos que el input del usuario es correcto y en caso erroneo repetimos
			// la petición

			if ((orden.equals("UP")) || (orden.equals("DOWN"))) {

				opcionCorrecta = true;
			}

			else {
				System.out.println("Has cometido un error. Introduce la opción correcta");
			}

		} while (opcionCorrecta == false);

		// Con la petición correcta, hacemos la lógica para ordenar de menor a mayor
		// siempre

		// Comprobamos si el primer número es el menor de los tres

		if ((num1 <= num2) && (num1 <= num3)) {

			numOrdenado1 = num1;

			// Ordenamos los otros dos

			if (num2 <= num3) {
				numOrdenado2 = num2;
				numOrdenado3 = num3;

			} else {
				numOrdenado3 = num2;
				numOrdenado2 = num3;
			}
		}

		// Comprobamos si el segundo número es el menor de los tres

		else if ((num2 <= num1) && (num2 <= num3)) {

			numOrdenado1 = num2;

			// Ordenamos los otros dos

			if (num1 <= num3) {
				numOrdenado2 = num1;
				numOrdenado3 = num3;

			} else {
				numOrdenado3 = num1;
				numOrdenado2 = num3;
			}
		}

		// El caso que queda es cuando el tercer número es el menor

		else {

			numOrdenado1 = num3;

			// Ordenamos los otros dos

			if (num1 <= num2) {
				numOrdenado2 = num1;
				numOrdenado3 = num2;

			} else {
				numOrdenado3 = num1;
				numOrdenado2 = num2;
			}

		}

		// Ya está ordenado de menor a mayor, sólo tenemos que imprimir por consola

		if (orden.equals("UP")) {

			System.out.print("Orden Ascendente: ");
			System.out.print(numOrdenado1 + ", " + numOrdenado2 + ", " + numOrdenado3);

		}

		// En el caso DOWN como tenemos los valores ordenados para UP, sólo
		// intercambiamos los extremos

		else {
			numAux = numOrdenado3;
			numOrdenado3 = numOrdenado1;
			numOrdenado1 = numAux;

			System.out.print("Orden Descendente: ");
			System.out.print(numOrdenado1 + ", " + numOrdenado2 + ", " + numOrdenado3);
		}

		// Cerramos el objeto Scanner para evitar errores posteriores

		scanner.close();

	}

}
