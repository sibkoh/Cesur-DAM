public class Ejercicio3 {

	public static void main(String[] args) {
		// Crear objeto de la clase Persona
		Persona persona = new Persona();

		// Asignar directamente el valor al campo 'nombre'
		persona.setNombre("Juan");

		// Mostrar el valor del campo 'nombre'
		System.out.println("Nombre antes de la refactorización: " + persona.getNombre());

		// Modificar el campo 'nombre' directamente
		persona.setNombre("Carlos");

		// Mostrar el nuevo valor del campo 'nombre'
		System.out.println("Nuevo nombre después de la refactorización: " + persona.getNombre());
	}

	public static class Persona {
		// El campo 'nombre' es público, por lo que es accesible directamente
		private String nombre;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
}
