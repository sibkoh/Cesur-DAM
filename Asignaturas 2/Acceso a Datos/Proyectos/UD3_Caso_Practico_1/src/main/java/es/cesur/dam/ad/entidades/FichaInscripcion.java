package es.cesur.dam.ad.entidades;

// Importamos las herramientas de persistencia (JPA)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CLASE ENTIDAD (Entity) Esta clase es el "plano" de nuestra tabla en la base
 * de datos. Hibernate leerá estas anotaciones y creará la tabla
 * automáticamente.
 */
@Entity // 1. Dice a Hibernate: "Esta clase se tiene que convertir en una tabla".
@Table(name = "tb_inscripciones_2026") // 2. Opcional: Define el nombre exacto de la tabla en MySQL.
public class FichaInscripcion {

	// --- CLAVE PRIMARIA (PK) ---
	@Id // Marca este campo como la Clave Primaria (Primary Key).
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Dice: "Que MySQL autoincremente este número (1, 2, 3...)"
	@Column(name = "id_matricula") // Nombre de la columna en la BD.
	private Long id;

	// --- COLUMNAS NORMALES ---
	@Column(name = "nombre_pila", nullable = false) // 'nullable=false' significa que es obligatorio (NOT NULL).
	private String nombre;

	@Column(name = "apellidos", nullable = false)
	private String apellidos;

	@Column(name = "curso_academico")
	private String curso;

	@Column(name = "edad_alumno")
	private int edad;

	@Column(name = "total_asignaturas")
	private int numAsignaturas;

	// --- CONSTRUCTORES ---

	// CONSTRUCTOR VACÍO (OBLIGATORIO):
	// Hibernate usa "Reflexión" para crear objetos. Necesita poder instanciar la
	// clase sin argumentos
	// antes de rellenar los datos desde la base de datos. Si lo borras, Hibernate
	// fallará.
	public FichaInscripcion() {
	}

	// CONSTRUCTOR CON DATOS:
	// Para que nosotros (los programadores) podamos crear objetos nuevos fácilmente
	// en el Main.
	// Fíjate que NO incluimos el 'id', porque el ID lo genera la base de datos
	// automáticamente.
	public FichaInscripcion(String nombre, String apellidos, String curso, int edad, int numAsignaturas) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.curso = curso;
		this.edad = edad;
		this.numAsignaturas = numAsignaturas;
	}

	// --- GETTERS Y SETTERS ---
	// Hibernate usa estos métodos para leer y escribir en las propiedades privadas.
	// Son el puente entre la propiedad privada y el mundo exterior.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getNumAsignaturas() {
		return numAsignaturas;
	}

	public void setNumAsignaturas(int numAsignaturas) {
		this.numAsignaturas = numAsignaturas;
	}

	// Método toString: Para que al hacer System.out.println(alumno) salga algo
	// legible y no códigos raros.
	@Override
	public String toString() {
		return "Ficha [" + id + "] - " + nombre + " " + apellidos + " (" + curso + ")";
	}
}