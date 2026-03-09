package org.asociacion.compras.dao;

import java.util.Date; // IMPORTANTE: Modificado a java.util.Date
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.asociacion.compras.config.GestorConexionObjectDB;
import org.asociacion.compras.modelo.ClienteEditorial;

/**
 * Clase DAO (Data Access Object) para concentrar todas las sentencias de base
 * de datos. Aísla el resto de la aplicación de la lógica estricta de JPA.
 */
public class RepositorioClientesCrud {

	// CREATE (Crear)
	public void guardarCliente(ClienteEditorial cliente) {
		EntityManager em = GestorConexionObjectDB.obtenerManejador();
		try {
			em.getTransaction().begin(); // Iniciamos transacción (ACID)
			em.persist(cliente); // Guarda el objeto en la BD
			em.getTransaction().commit();// Confirmamos escritura física
		} catch (Exception e) {
			em.getTransaction().rollback(); // Si algo falla, revertimos para no dejar datos corruptos
			System.err.println("Error crítico al guardar en ObjectDB: " + e.getMessage());
		} finally {
			em.close(); // Siempre liberamos el manejador, incluso si hay error
		}
	}

	// READ (Leer todos)
	public List<ClienteEditorial> listarTodos() {
		EntityManager em = GestorConexionObjectDB.obtenerManejador();
		// Usamos JPQL (Java Persistence Query Language) orientado a objetos, no SQL
		// tradicional.
		// Seleccionamos la clase 'ClienteEditorial', no una supuesta tabla 'clientes'.
		TypedQuery<ClienteEditorial> query = em.createQuery("SELECT c FROM ClienteEditorial c", ClienteEditorial.class);
		List<ClienteEditorial> lista = query.getResultList();
		em.close();
		return lista;
	}

	// UPDATE (Actualizar - Añadir la visita al Array/Lista)
	public void añadirVisitaACliente(Long idCliente, Date fechaVisita) { // CÁMBIO APLICADO: Recibe un Date
		EntityManager em = GestorConexionObjectDB.obtenerManejador();
		try {
			em.getTransaction().begin();
			// find() busca el objeto por su Clave Primaria (@Id)
			ClienteEditorial cliente = em.find(ClienteEditorial.class, idCliente);

			if (cliente != null) {
				// Modificamos el objeto en memoria RAM
				cliente.registrarVisita(fechaVisita);
				// merge() empuja los cambios de la RAM al archivo físico .odb de ObjectDB
				em.merge(cliente);
				System.out.println("-> Visita registrada correctamente con fecha: " + fechaVisita);
			} else {
				System.out.println("-> ERROR: No existe ningún representante con el ID: " + idCliente);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	// DELETE (Borrar)
	public void eliminarCliente(Long id) {
		EntityManager em = GestorConexionObjectDB.obtenerManejador();
		try {
			em.getTransaction().begin();
			ClienteEditorial cliente = em.find(ClienteEditorial.class, id);

			if (cliente != null) {
				// remove() elimina el objeto físico de la BDOO
				em.remove(cliente);
				System.out.println("-> Cliente y todo su historial de visitas han sido eliminados.");
			} else {
				System.out.println("-> ERROR: Imposible borrar. ID no encontrado.");
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}