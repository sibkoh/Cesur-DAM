import java.util.Scanner;

public class Ejercicio4 {

	public static void main(String[] args) {

		// Creamos la variable para almacenar el número a recibir por consola,
		// usamos float porque es el tipo decimal más pequeño y no necesitaremos más y
		// ahorramos memoria

		float peso = 0;
		float altura = 0;
		float imc = 0;

		// Creamos variables bool, para usarlas de flag y controle cuando cada input sea
		// correcto

		Boolean pesoNoCorrecto = true;
		Boolean alturaNoCorrecta = true;

		// Creamos una variable String para almacenar la conclusión del IMC

		String nivelDePeso = "";

		// Creamos un nuevo objeto tipo Scanner, específico para leer tokens desde el
		// buffer de teclado

		Scanner scanner = new Scanner(System.in);

		// Creamos un bucle que se repetirá hasta que la condición de los inputs de
		// usuario
		// sea la correcta

		do {

			// Imprimimos por consola el mensaje para pedir los datos. El \n es un salto de
			// línea

			System.out.println(
					"\nIntroduce tu peso, si tiene decimales usa coma, no punto (El peso debe ser igual o mayor a 30 kg y menor o igual a 300 kgr): ");

			// Almacenamos el resultado del input en la variable, el metodo nextFloat() solo
			// lee el número y no el salto de línea
			// no da error, si encuentra espacios antes del numero, pero sí ocurre, si input
			// no es un número
			// nextFLoat() convertirá un número entero en float, así que no dará error

			peso = scanner.nextFloat();

			if (peso < 30 || peso > 300) {

				System.out.println("\nError. Vuelve a introducir un peso válido");

			} else {

				// Ponemos el flag que controla este bucle a falso porque el primer input es
				// correcto, eso terminará el bucle

				pesoNoCorrecto = false;

				System.out.println("\npeso valido");

				// Repetimos la estructura de bucle anterior, pero dentro de la condición si el
				// primer dato es correcto

				do {

					System.out.println(
							"\nIntroduce tu altura, si tiene decimales usa coma, no punto (La altura debe estar comprendida entre 1.30 metros y 2 metros): ");

					altura = scanner.nextFloat();

					if (altura < 1.30 || altura > 2.00) {

						System.out.println("\nError. Vuelve a introducir una altura válida");

					} else {

						alturaNoCorrecta = false;

						System.out.println("\naltura valida");
					}

				} while (alturaNoCorrecta == true);

			}

		} while (pesoNoCorrecto == true);

		// Cerramos el objeto Scanner para evitar errores posteriores

		// Procedemos a calcular el IMC

		imc = peso / (altura * altura);

		// Procedemos a establecer el nivel de peso en una estructura condicional
		// anidada para cada caso

		if (imc < 18.5) {

			nivelDePeso = "Bajo peso";

		} else if (imc >= 18.5 && imc <= 24.9) {

			nivelDePeso = "Normal";

		} else if (imc >= 25 && imc <= 29.9) {

			nivelDePeso = "Sobrepeso";

		} else {

			nivelDePeso = "Obesidad";

		}

		// Imprimimos el resultado

		System.out.print("\nTu IMC es de : " + imc + " lo que corresponde a un nivel de peso de: " + nivelDePeso);

		// Cerramos el objeto Scanner para evitar errores posteriores

		scanner.close();
	}

}
