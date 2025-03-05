public class Ejercicio1 {

	public static void main(String[] args) {
		// Crear un objeto de la clase Persona
		Persona persona = new Persona();
		persona.setX("Juan");

		// Imprimir el valor de x
		System.out.println("Nombre: " + persona.getX());
	}
}

// Definir la clase Persona fuera del método main()
class Persona {
	public String nombre;

	public void setX(String x) {
		this.nombre = x;
	}

	public String getX() {
		return this.nombre;
	}
}
