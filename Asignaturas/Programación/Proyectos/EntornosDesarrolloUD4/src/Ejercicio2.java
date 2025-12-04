public class Ejercicio2 {

	public static void main(String[] args) {
		// Crear un objeto de la clase Coche
		Vehiculo miCoche = new Coche();

		// Llamar a los métodos
		miCoche.acelerar();
		miCoche.frenar();
	}

	public static class Coche implements Vehiculo {
		@Override
		public void acelerar() {
			System.out.println("Acelerando el coche");
		}

		@Override
		public void frenar() {
			System.out.println("Frenando el coche");
		}

	}
}