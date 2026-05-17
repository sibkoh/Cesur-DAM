package com.empresa.rrhh.ui;

/**
 * Clase Lanzadora (Wrapper) Su única responsabilidad es sortear la restricción
 * de módulos de Java 11+ al iniciar una aplicación JavaFX desde el Classpath de
 * Maven.
 */
public class Launcher {

	public static void main(String[] args) {
		// En lugar de arrancar nosotros, delegamos la llamada
		// al método main de nuestra verdadera clase gráfica.
		PanelFichajesFX.main(args);
	}
}