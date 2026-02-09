package es.cesur.dam.ad.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * CLASE DE UTILIDAD (Singleton) Su única misión es arrancar Hibernate y darnos
 * la "Fábrica de Sesiones". Crear la SessionFactory es muy costoso (lento), así
 * que esta clase asegura que solo se crea UNA VEZ para toda la aplicación.
 */
public class HibernateUtil {

	// La variable estática que guardará la fábrica única.
	private static SessionFactory sessionFactory;

	// Bloque estático: Se ejecuta UNA sola vez, justo cuando se carga la clase.
	static {
		try {
			System.out.println("🔧 Iniciando configuración de Hibernate...");

			// 1. Crea una configuración vacía.
			// 2. .configure(): Lee el archivo 'hibernate.cfg.xml' automáticamente.
			// 3. .buildSessionFactory(): Construye la fábrica con esa configuración.
			sessionFactory = new Configuration().configure().buildSessionFactory();

			System.out.println("✅ Hibernate iniciado correctamente. Conexión establecida.");
		} catch (Throwable ex) {
			// Si algo falla (ej: MySQL apagado, contraseña mal), lo mostramos en rojo.
			System.err.println("❌ Error crítico en la inicialización de Hibernate: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	// Método público para que el resto del programa pueda pedir la fábrica.
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// Método para apagar Hibernate limpiamente al cerrar la app.
	// Si no hacemos esto, la conexión con la base de datos podría quedarse
	// "colgada".
	public static void shutdown() {
		if (sessionFactory != null) {
			getSessionFactory().close();
		}
	}
}