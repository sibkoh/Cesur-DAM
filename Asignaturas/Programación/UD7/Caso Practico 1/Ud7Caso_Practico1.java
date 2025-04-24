public class Ud7Caso_Practico1 {

	public static void main(String[] args) {

		// Creamos un coche Km0 que se puede alquilar
		CocheKm0 cocheKm0 = new CocheKm0("1234-ABC", "Blanco", "Toyota Yaris", 14500, 50);
		System.out.println(cocheKm0);
		cocheKm0.alquilar();

		System.out.println("--------------------------------------------------");

		// Creamos un coche nuevo que se puede vender
		CocheNuevo cocheNuevo = new CocheNuevo("5678-DEF", "Negro", "Tesla Model 3", 40000, 0, 5);
		System.out.println(cocheNuevo);
		cocheNuevo.vender();

		System.out.println("--------------------------------------------------");

		// Creamos un coche de segunda mano que se puede vender
		CocheSegundaMano cocheSegundaMano = new CocheSegundaMano("9101-GHI", "Rojo", "Seat Ibiza", 8000, 35000,
				"Luis Martínez");
		System.out.println(cocheSegundaMano);
		cocheSegundaMano.vender();

		System.out.println("--------------------------------------------------");

		// Mostramos cuántos coches hay en stock
		System.out.println("Número total de coches en stock: " + Coche.getNumCochesStock());
	}
}

// Clase abstracta base para todos los tipos de coches
abstract class Coche {
	protected String matricula;
	protected String color;
	protected String modelo;
	protected double precio;
	protected int kilometros;

	// Atributo estático para contar cuántos coches se han creado
	protected static int numCochesStock = 0;

	public Coche(String matricula, String color, String modelo, double precio, int kilometros) {
		this.matricula = matricula;
		this.color = color;
		this.modelo = modelo;
		this.precio = precio;
		this.kilometros = kilometros;
		numCochesStock++; // Se incrementa cada vez que se crea un coche
	}

	public static int getNumCochesStock() {
		return numCochesStock;
	}

	// Sobrescribimos toString para mostrar información básica del coche
	@Override
	public String toString() {
		return "Matrícula: " + matricula + "\nColor: " + color + "\nModelo: " + modelo + "\nPrecio: " + precio
				+ " €\nKilómetros: " + kilometros;
	}
}

// Interface para coches que se pueden vender
interface Vendible {
	void vender();
}

// Interface para coches que se pueden alquilar
interface Alquilable {
	void alquilar();
}

// CocheKm0 implementa la interfaz Alquilable
class CocheKm0 extends Coche implements Alquilable {

	public CocheKm0(String matricula, String color, String modelo, double precio, int kilometros) {
		super(matricula, color, modelo, precio, kilometros);
	}

	@Override
	public void alquilar() {
		System.out.println("El coche km0 se ha alquilado");
	}

	@Override
	public String toString() {
		return "Tipo: Coche Km0\n" + super.toString();
	}
}

// CocheNuevo implementa Vendible
class CocheNuevo extends Coche implements Vendible {

	private int duracionGarantia; // en años

	public CocheNuevo(String matricula, String color, String modelo, double precio, int kilometros,
			int duracionGarantia) {
		super(matricula, color, modelo, precio, kilometros);
		this.duracionGarantia = duracionGarantia;
	}

	@Override
	public void vender() {
		System.out.println("El coche nuevo se ha vendido");
	}

	@Override
	public String toString() {
		return "Tipo: Coche Nuevo\n" + super.toString() + "\nGarantía: " + duracionGarantia + " años";
	}
}

// CocheSegundaMano implementa Vendible
class CocheSegundaMano extends Coche implements Vendible {

	private String nombreAntiguoPropietario;

	public CocheSegundaMano(String matricula, String color, String modelo, double precio, int kilometros,
			String nombreAntiguoPropietario) {
		super(matricula, color, modelo, precio, kilometros);
		this.nombreAntiguoPropietario = nombreAntiguoPropietario;
	}

	@Override
	public void vender() {
		System.out.println("El coche de segunda mano se ha vendido");
	}

	@Override
	public String toString() {
		return "Tipo: Coche de Segunda Mano\n" + super.toString() + "\nAntiguo propietario: "
				+ nombreAntiguoPropietario;
	}
}
