
public class Ejercicio5 {

	public static void main(String[] args) {

		// Comentario sobre el ejercicio propuesto

		System.out.println("Números entre el 1 y el 100 que son múltiplos de 2 o de 3: \n");

		// Bucle for porque sabemos que queremos iterar entre los números 1 y 100
		// incluidos de uno en uno

		for (byte i = 1; i <= 100; ++i) {

			// Si el resto de dividir entre 2 o entre 3 es 0, entonces el número es múltiplo
			// y lo imprimimos

			if (i % 2 == 0 || i % 3 == 0) {

				System.out.print(i + " ");
			}
		}
	}

}
