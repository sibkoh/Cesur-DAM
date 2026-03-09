package org.asociacion.compras.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; // IMPORTANTE: Usamos la clase Date clásica que JPA procesa nativamente
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entidad que representa a un representante de una editorial de juegos. Anotada
 * con @Entity para que ObjectDB sepa que debe hacer persistente esta clase.
 */
@Entity
public class ClienteEditorial implements Serializable {

	// Clave primaria autoincremental gestionada por la BDOO
	@Id
	@GeneratedValue
	private Long id;

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String comercialPrincipal;
	private String idEmpresa; // Identificador interno de la asociación. Ej: "ZACATRUS-001"

	/**
	 * @ElementCollection le dice a JPA que esto no es una relación pura con
	 *                    otra @Entity, sino una colección de tipos básicos (Date).
	 *                    En una BD Relacional crearía una tabla anexa, pero en
	 *                    ObjectDB, sencillamente serializa la lista dentro del
	 *                    mismo objeto de forma nativa. fetch = FetchType.EAGER
	 *                    fuerza a que las fechas se carguen en memoria tan pronto
	 *                    como consultemos al cliente.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Date> fechasVisitas; // CÁMBIO APLICADO: Ahora es una lista de Date

	/**
	 * Constructor vacío requerido OBLIGATORIAMENTE por la especificación JPA. Lo
	 * aprovechamos para inicializar la lista y evitar temidos
	 * NullPointerExceptions.
	 */
	public ClienteEditorial() {
		this.fechasVisitas = new ArrayList<>();
	}

	/**
	 * Constructor sobrecargado para facilitar la creación de objetos desde la
	 * consola. No incluimos el 'id' porque de eso se encarga el @GeneratedValue.
	 */
	public ClienteEditorial(String nombre, String apellido1, String apellido2, String comercialPrincipal,
			String idEmpresa) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.comercialPrincipal = comercialPrincipal;
		this.idEmpresa = idEmpresa;
		this.fechasVisitas = new ArrayList<>();
	}

	// --- GETTERS Y SETTERS ---
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getComercialPrincipal() {
		return comercialPrincipal;
	}

	public void setComercialPrincipal(String comercialPrincipal) {
		this.comercialPrincipal = comercialPrincipal;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<Date> getFechasVisitas() {
		return fechasVisitas;
	}

	/**
	 * Método de lógica de negocio encapsulada. En lugar de pedir la lista y hacer
	 * un '.add()' desde fuera, le decimos al propio cliente que registre su visita.
	 */
	public void registrarVisita(Date fecha) { // CÁMBIO APLICADO: Recibe un Date
		this.fechasVisitas.add(fecha);
	}

	@Override
	public String toString() {
		return "ID: [" + id + "] | Representante: " + nombre + " " + apellido1 + " | Editorial: " + idEmpresa
				+ " | Total Visitas Realizadas: " + fechasVisitas.size() + " | Últimas visitas: " + fechasVisitas;
	}
}