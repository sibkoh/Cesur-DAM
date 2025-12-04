public class Ejercicio4 {

	public static void main(String[] args) {
		// Crear objeto de la clase Calculadora
		Calculadora calculadora = new Calculadora();

		// Llamar al método refactorizado
		calculadora.realizarOperacion();
	}

	public static class Calculadora {
		public void realizarOperacion() {
			int resultado3 = calcularResultado();
			System.out.println("Resultado: " + resultado3);
		}

		private int calcularResultado() {
			int resultado1 = 5 * 2;
			int resultado2 = 10 / 2;
			int resultado3 = resultado1 + resultado2;
			return resultado3;
		}
	}

}
