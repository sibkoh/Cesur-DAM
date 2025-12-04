public class Division {
	private int num1;
	private int num2;

	// Constructor
	public Division(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
	}

	// Método para realizar la división
	public double dividir() {
		if (num2 == 0) {
			throw new ArithmeticException("No se puede dividir por cero");
		}
		return (double) num1 / num2;
	}
}
