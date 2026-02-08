package es.cesur.dam.ad.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import es.cesur.dam.ad.entidades.FichaInscripcion;
import es.cesur.dam.ad.util.HibernateUtil;

/**
 * CLASE DAO (Data Access Object) hace el trabajo de SQL pero usando objetos.
 * Aquí implementamos el CRUD completo y la consulta SQL nativa.
 */
public class GestorMatriculas {

	// ----------------------------------------------------------------
	// 1. CREATE (Insertar): Guardar un nuevo alumno
	// ----------------------------------------------------------------
	public void altaAlumno(FichaInscripcion alumno) {
		Transaction transaction = null;
		// 'try-with-resources': Abre la sesión y la cierra automáticamente al terminar.
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Paso 1: Iniciamos la transacción.
			// Imaginad que es como abrir un "paréntesis" de seguridad.
			transaction = session.beginTransaction();

			// Paso 2: Guardamos.
			// El objeto 'alumno' pasa de estar solo en memoria (Transient) a estar vigilado
			// por Hibernate (Persistent).
			session.persist(alumno);

			// Paso 3: Confirmamos (Commit).
			// Aquí es donde realmente se escribe en el disco duro de la base de datos.
			transaction.commit();

			System.out.println(" Alumno guardado correctamente con ID: " + alumno.getId());

		} catch (Exception e) {
			// Si algo falla, hacemos 'Rollback' (deshacer cambios) para no dejar datos
			// corruptos.
			if (transaction != null)
				transaction.rollback();
			System.out.println(" Error al intentar guardar el alumno.");
			e.printStackTrace(); // Muestra el error técnico por consola
		}
	}

	// ----------------------------------------------------------------
	// 2. READ (Leer): Buscar un alumno por su ID
	// ----------------------------------------------------------------
	public FichaInscripcion obtenerAlumno(Long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// El método .get() es mágico: le das la clase y el ID, y él te devuelve el
			// objeto relleno.
			// Si no lo encuentra, devuelve null.
			return session.get(FichaInscripcion.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	// ----------------------------------------------------------------
	// 3. UPDATE (Modificar): Cambiar datos de un alumno
	// ----------------------------------------------------------------
	public void actualizarAlumno(FichaInscripcion alumnoModificado) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// Usamos .merge() en lugar de .update().
			// ¿Por qué? Porque a veces el objeto viene "desconectado" (Detached) del menú
			// principal.
			// Merge lo vuelve a conectar y actualiza solo lo que haya cambiado.
			session.merge(alumnoModificado);

			transaction.commit();
			System.out.println(" Datos del alumno actualizados en la base de datos.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------
	// 4. DELETE (Borrar): Eliminar un alumno
	// ----------------------------------------------------------------
	public boolean borrarAlumno(Long id) {
		Transaction transaction = null;
		boolean exito = false;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// Antes de borrar, primero buscamos el objeto para asegurar que existe.
			FichaInscripcion alumno = session.get(FichaInscripcion.class, id);

			if (alumno != null) {
				// Si existe, lo borramos.
				session.remove(alumno);
				exito = true;
				System.out.println(" Alumno eliminado del sistema.");
			} else {
				System.out.println(" No se puede borrar: El ID no existe.");
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return exito; // Devolvemos true si se borró, false si no.
	}

	// ----------------------------------------------------------------
	// LISTAR TODOS (HQL - Hibernate Query Language)
	// ----------------------------------------------------------------
	public List<FichaInscripcion> obtenerTodos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Fíjate: NO usamos 'SELECT * FROM tabla'.
			// Usamos 'FROM FichaInscripcion' (el nombre de la CLASE Java).
			Query<FichaInscripcion> query = session.createQuery("from FichaInscripcion", FichaInscripcion.class);
			return query.list(); // Nos devuelve una lista de objetos Java directa.
		}
	}

	// ----------------------------------------------------------------
	// CONSULTA SQL NATIVA
	// Aquí podemos usar SQL puro si hace falta.
	// ----------------------------------------------------------------
	public Double obtenerEdadMedia() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Escribimos SQL tal cual lo haríamos en Workbench
			String sql = "SELECT AVG(edad_alumno) FROM tb_inscripciones_2026";

			// createNativeQuery se usa para SQL puro
			Query<Double> query = session.createNativeQuery(sql, Double.class);

			// getSingleResult nos da el único dato que devuelve la consulta (la media)
			return query.getSingleResult();
		} catch (Exception e) {
			return 0.0; // Si no hay alumnos o falla, devolvemos 0
		}
	}
}