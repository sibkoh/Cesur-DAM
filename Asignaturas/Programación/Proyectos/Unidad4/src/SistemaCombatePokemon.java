// Clase principal que contiene el método main

public class SistemaCombatePokemon {
	public static void main(String[] args) {

		// Crear dos entrenadores usando ambos constructores

		Entrenador ash = new Entrenador("Ash", 3, 15, 4);
		Entrenador misty = new Entrenador();
		misty.setNombre("Misty");
		misty.setExperiencia(5);
		misty.setEdad(16);
		misty.setInsignias(6);

//		 Entrenador extra por si se quiere otro resultado
//		 Entrenador brock = new Entrenador("Brock", 4, 18, 5);

		// Asignar Pokémon a los entrenadores

		ash.setPokemon(new PokemonFuego("Charizard", 42, 150, 85, 120));
		misty.setPokemon(new PokemonAgua("Starmie", 40, 130, 200, 95));

//		 Pokémon extra por si se quiere otro resultado
//		 brock.setPokemon(new PokemonPlanta("Venosaur", 43, 200, 160, 110));

		// Mostrar datos de los entrenadores

		System.out.println("--- DATOS DE LOS ENTRENADORES ---");
		System.out.println(ash);
		System.out.println(misty);

//		 Mostrar entrenador extra
//		 System.out.println(brock);

		// Realizar combate

		System.out.println("\n--- COMBATE POKÉMON ---\n");
		Combate combate = new Combate();
		String resultado = combate.iniciarCombate(ash.getPokemon(), misty.getPokemon());

//		 Combate diferente
//		 String resultado = combate.iniciarCombate(misty.getPokemon(),
//		 brock.getPokemon());

		// Mostrar resultado

		System.out.println(resultado);
	}
}

/**
 * Superclase Pokémon (accesibilidad: public para permitir herencia) Atributos
 * privados para garantizar encapsulamiento
 */

class Pokemon {
	private String nombre; // Privado: solo accesible mediante getters
	private int nivel; // Privado: validar mediante setters
	private double salud; // Privado: controlar modificaciones

	// Constructores

	public Pokemon() {
	}

	public Pokemon(String nombre, int nivel, double salud) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.salud = salud;
	}

	// Getters y setters (públicos para acceso controlado)

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		if (nivel > 0)
			this.nivel = nivel;
	}

	public double getSalud() {
		return salud;
	}

	public void setSalud(double salud) {
		if (salud >= 0)
			this.salud = salud;
	}
}

// ----------------- SUBCLASES POKÉMON -----------------

class PokemonFuego extends Pokemon {
	private int potenciaFuego; // Privado: específico de esta subclase
	private double velocidad; // Privado: encapsulado correctamente

	public PokemonFuego(String nombre, int nivel, double salud, int potenciaFuego, double velocidad) {
		super(nombre, nivel, salud); // Llamada al constructor padre
		this.potenciaFuego = potenciaFuego;
		this.velocidad = velocidad;
	}

	// Método específico

	public String lanzarLlamarada() {
		return "¡Lanzar llamarada con potencia " + potenciaFuego + "!";
	}

	// Getters/Setters

	public int getPotenciaFuego() {
		return potenciaFuego;
	}

	public void setPotenciaFuego(int potenciaFuego) {
		this.potenciaFuego = potenciaFuego;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
}

class PokemonAgua extends Pokemon {
	private double presionAgua; // Privado: encapsulación correcta
	private int defensaAcuatica; // Privado: solo modificable mediante setters

	public PokemonAgua(String nombre, int nivel, double salud, double presionAgua, int defensaAcuatica) {
		super(nombre, nivel, salud);
		this.presionAgua = presionAgua;
		this.defensaAcuatica = defensaAcuatica;
	}

	// Método específico

	public String chorroAgua() {
		return "¡Chorro de agua a " + presionAgua + " PSI!";
	}

	// Getters/Setters

	public double getPresionAgua() {
		return presionAgua;
	}

	public void setPresionAgua(double presionAgua) {
		this.presionAgua = presionAgua;
	}

	public int getDefensaAcuatica() {
		return defensaAcuatica;
	}

	public void setDefensaAcuatica(int defensaAcuatica) {
		this.defensaAcuatica = defensaAcuatica;
	}
}

class PokemonPlanta extends Pokemon {
	private int semillasCuracion; // Privado: encapsulado
	private double tasaCrecimiento;

	public PokemonPlanta(String nombre, int nivel, double salud, int semillasCuracion, double tasaCrecimiento) {
		super(nombre, nivel, salud);
		this.semillasCuracion = semillasCuracion;
		this.tasaCrecimiento = tasaCrecimiento;
	}

	// Método específico

	public String latigoCepa() {
		return "¡Látigo cepa con " + semillasCuracion + " semillas!";
	}

	// Getters/Setters

	public int getSemillasCuracion() {
		return semillasCuracion;
	}

	public void setSemillasCuracion(int semillasCuracion) {
		this.semillasCuracion = semillasCuracion;
	}

	public double getTasaCrecimiento() {
		return tasaCrecimiento;
	}

	public void setTasaCrecimiento(double tasaCrecimiento) {
		this.tasaCrecimiento = tasaCrecimiento;
	}
}

// ----------------- CLASE ENTRENADOR -----------------

class Entrenador {
	private String nombre; // Privado: encapsulación
	private int experiencia; // Privado: solo modificable mediante métodos
	private int edad; // Privado: dato sensible
	private int insignias; // Privado: validar en setters
	private Pokemon pokemon; // Asociación 1 a 1 (privada)

	// Constructores

	public Entrenador() {
	}

	public Entrenador(String nombre, int experiencia, int edad, int insignias) {
		this.nombre = nombre;
		this.experiencia = experiencia;
		this.edad = edad;
		this.insignias = insignias;
	}

	// Getters/Setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		if (experiencia >= 0)
			this.experiencia = experiencia;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		if (edad > 0)
			this.edad = edad;
	}

	public int getInsignias() {
		return insignias;
	}

	public void setInsignias(int insignias) {
		if (insignias >= 0)
			this.insignias = insignias;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	// En esta parte sobrescribimos el método toString() de la Clase Objeto que es
	// padre de todas las clases
	// el método por defecto imprimiría los datos de la clase con un formato en
	// linea, por lo que quedaría
	// antiestético, así que redefiniendo el comportamiento de toString() la salida
	// del println(entrenador)
	// es más legible.

	@Override
	public String toString() {
		return "\nEntrenador: " + nombre + "\nExperiencia: " + experiencia + "\nEdad: " + edad + "\nInsignias: "
				+ insignias + "\nPokémon: " + pokemon.getNombre() + " (Nivel " + pokemon.getNivel() + ")";
	}
}

// ----------------- CLASE COMBATE -----------------

class Combate {

	// Método público para iniciar combate

	public String iniciarCombate(Pokemon p1, Pokemon p2) {
		System.out.println(p1.getNombre() + " vs " + p2.getNombre());

		if (p1.getNivel() > p2.getNivel()) {
			return "Ganador: " + p1.getNombre() + " (Nivel " + p1.getNivel() + ")";
		} else if (p2.getNivel() > p1.getNivel()) {
			return "Ganador: " + p2.getNombre() + " (Nivel " + p2.getNivel() + ")";
		} else {
			return "¡Empate! Ambos Pokémon son nivel " + p1.getNivel();
		}
	}
}