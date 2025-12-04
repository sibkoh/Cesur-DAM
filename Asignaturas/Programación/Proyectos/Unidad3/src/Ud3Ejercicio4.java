
public class Ud3Ejercicio4 {

	// Declaramos todas las clases antes del método main() para poder
	// luego acceder a sus atributos y métodos desde el mismo

	// Tenemos que hacer las clases static, para no tener luego problemas al crear
	// objetos en el método main

	// Clase Empleado (SuperClase)

	static class Empleado {

		// Atributos privados inicializados aquí en lugar de en el constructor sin
		// parámetros

		private int idEmpleado = 0;
		private String nombre = "Empleado";
		private String apellidos = "Por defecto";
		private double salario = 0.0d;

		// Constructor sin parámetros, sin valores impuestos por defecto por mi

		public Empleado() {

			// Hemos agregado un print, para que cuando se cree un objeto nos informe por
			// consola de la acción

			System.out.println("Se ha creado objeto Empleado sin parámetros");
		}

		// Constructor con parámetros

		public Empleado(int idEmpleado, String nombre, String apellidos, double salario) {

			this.idEmpleado = idEmpleado;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.salario = salario;

			System.out.println("Se ha creado objeto Empleado con los parámetros introducidos");
		}

		// Getters públicos para poder acceder al valor de los atributos privados
		// cada uno devuelve el valor del atributo indicado

		public int getIdEmpleado() {

			return idEmpleado;
		}

		public String getNombre() {

			return nombre;
		}

		public String getApellidos() {

			return apellidos;
		}

		public double getSalario() {

			return salario;
		}

		// Setters públicos para poder cambiar el valor de los atributos privados
		// evitamos poner salario como parámetro para no tener que usar this.salario
		// y así diferenciar entre el atributo de la clase y el parámatro.

		public void setSalario(double nuevoSalario) {

			salario = nuevoSalario;
		}

	}

	// Las subclases, deberían hacer extend de la clase Empleado, pero en este
	// ejercicio las subclases
	// las implementamos como independientes, así que no heredarán ningún atributo o
	// método desde Empleado

	// Clase Administrativo (Subclase)

	static class Administrativo {

		// Atributos específicos de Administrativo inicializados

		private String areaDeTrabajo = "Por defecto";

		private int nivelAcceso = 0;

		// Constructor sin parámetros

		public Administrativo() {

			System.out.println("Se ha creado objeto Administrativo sin parámetros");
		}

		// Constructor con parámetros, no usaremos el método super() para crear e
		// inicializar los atributos
		// de la Clase Empleado porque no estamos heredando de la clase, es una clase
		// independiente

		public Administrativo(String areaDeTrabajo, int nivelAcceso) {

			this.areaDeTrabajo = areaDeTrabajo;
			this.nivelAcceso = nivelAcceso;

			System.out.println("Se ha creado objeto Administrativo con los parámetros introducidos");
		}

		// Getters y Setters

		public String getAreaDeTrabajo() {
			return areaDeTrabajo;
		}

		public void setAreaDeTrabajo(String nuevaAreaDeTrabajo) {
			areaDeTrabajo = nuevaAreaDeTrabajo;
		}

		public int getNivelAcceso() {
			return nivelAcceso;
		}

		public void setNivelAcceso(int nuevoNivelAcceso) {
			nivelAcceso = nuevoNivelAcceso;
		}

		// Métodos propios útiles de uso de la Clase

		public void gestionar() {

			System.out.println("Gestionando");
		}

		public void enseñarGestion() {

			System.out.println("Enseñando Gestión");
		}

		public void aprenderGestion() {

			System.out.println("Aprendiendo Gestión");
		}
	}

	// Clase Contable (Subclase)

	static class Contable {

		// Atributos específicos de Contable inicializados

		private String tipoDeImpuesto = "Por defecto";

		private int idProyectoAsignado = 0;

		// Constructor sin parámetros

		public Contable() {

			System.out.println("Se ha creado objeto Contable sin parámetros");
		}

		// Constructor con parámetros, no usaremos el método super() para crear e
		// inicializar los atributos
		// de la Clase Empleado porque no estamos heredando de la clase, es una clase
		// independiente

		public Contable(String tipoDeImpuesto, int idProyectoAsignado) {

			this.tipoDeImpuesto = tipoDeImpuesto;
			this.idProyectoAsignado = idProyectoAsignado;

			System.out.println("Se ha creado objeto Contable con los parámetros introducidos");
		}

		// Getters y Setters

		public String getTipoDeImpuesto() {
			return tipoDeImpuesto;
		}

		public void setTipoDeImpuesto(String nuevotipoDeImpuesto) {
			tipoDeImpuesto = nuevotipoDeImpuesto;
		}

		public int getidProyectoAsignado() {
			return idProyectoAsignado;
		}

		public void setidProyectoAsignado(int nuevaidProyectoAsignado) {
			idProyectoAsignado = nuevaidProyectoAsignado;
		}

		// Métodos propios útiles de uso de la Clase

		public void hacerContabilidad() {

			System.out.println("Haciendo Contabilidad");
		}

		public void enseñarContabilidad() {

			System.out.println("Enseñando Contabilidad");
		}

		public void aprenderContabilidad() {

			System.out.println("Aprendiendo Contabilidad");
		}
	}

	// Clase Informatico (Subclase)

	static class Informatico {

		// Atributos específicos de Informatico inicializados

		private String lenguajeDeProgramacion = "Java";

		private int idProyectoAsignado = 0;

		// Constructor sin parámetros

		public Informatico() {

			System.out.println("Se ha creado objeto Informatico sin parámetros");
		}

		// Constructor con parámetros, no usaremos el método super() para crear e
		// inicializar los atributos
		// de la Clase Empleado porque no estamos heredando de la clase, es una clase
		// independiente

		public Informatico(String lenguajeDeProgramacion, int idProyectoAsignado) {

			this.lenguajeDeProgramacion = lenguajeDeProgramacion;
			this.idProyectoAsignado = idProyectoAsignado;

			System.out.println("Se ha creado objeto Informatico con los parámetros introducidos");
		}

		// Getters y Setters

		public String getLenguajeDeProgramacion() {
			return lenguajeDeProgramacion;
		}

		public void setLenguajeDeProgramacion(String nuevoLenguajeDeProgramacion) {
			lenguajeDeProgramacion = nuevoLenguajeDeProgramacion;
		}

		public int getIdProyectoAsignado() {
			return idProyectoAsignado;
		}

		public void setIdProyectoAsignado(int nuevaIdProyectoAsignado) {
			idProyectoAsignado = nuevaIdProyectoAsignado;
		}

		// Métodos propios útiles de uso de la Clase

		public void Programar() {

			System.out.println("Programar");
		}

		public void enseñarProgramacion() {

			System.out.println("Enseñando Programacion");
		}

		public void aprenderProgramacion() {

			System.out.println("Aprendiendo Programacion");
		}
	}

	public static void main(String[] args) {

		// Creación de instancias (objetos) de cada clase para poder usar sus métodos.

		Empleado empleado1 = new Empleado(10, "Alejandro", "Muñoz", 1000.50);
		Administrativo administrativo1 = new Administrativo("Secretaria", 5);
		Contable contable1 = new Contable("IVA", 12345);
		Informatico informatico1 = new Informatico("C++", 1);

		// Mostrar los datos de los objetos creados usando los métodos get

		System.out.println("\nDatos de Empleado:");
		System.out.println("ID Empleado: " + empleado1.getIdEmpleado());
		System.out.println("Nombre: " + empleado1.getNombre());
		System.out.println("Apellidos: " + empleado1.getApellidos());
		System.out.println("Salario: " + empleado1.getSalario());
		System.out.println();

		// Llamar a métodos propios de Administrativo

		System.out.println("Datos de Administrativo:");
		System.out.println("Área de Trabajo: " + administrativo1.getAreaDeTrabajo());
		System.out.println("Nivel de Acceso: " + administrativo1.getNivelAcceso());
		administrativo1.gestionar();
		administrativo1.enseñarGestion();
		administrativo1.aprenderGestion();
		System.out.println();

		// Llamar a métodos propios de Contable

		System.out.println("Datos de Contable:");
		System.out.println("Tipo de Impuesto: " + contable1.getTipoDeImpuesto());
		System.out.println("ID Proyecto Asignado: " + contable1.getidProyectoAsignado());
		contable1.hacerContabilidad();
		contable1.enseñarContabilidad();
		contable1.aprenderContabilidad();
		System.out.println();

		// Llamar a métodos propios de Informático

		System.out.println("Datos de Informático:");
		System.out.println("Lenguaje de Programación: " + informatico1.getLenguajeDeProgramacion());
		System.out.println("ID Proyecto Asignado: " + informatico1.getIdProyectoAsignado());
		informatico1.Programar();
		informatico1.enseñarProgramacion();
		informatico1.aprenderProgramacion();
	}

}
