package prueba;

// Importamos nuestra propia librería. Al haber exportado el proyecto anterior 
// como un archivo "login.jar" y haberlo añadido al Build Path de este nuevo proyecto, 
// Java reconoce el paquete com.acceso como si fuera una librería oficial más.
import com.acceso.Login;

/**
 * Clase principal de pruebas. El objetivo de esta clase es demostrar que el
 * empaquetado (.jar) funciona perfectamente en un entorno completamente aislado
 * y ajeno al proyecto original.
 */
public class Prueba {

	public static void main(String[] args) {
		// Gracias a la programación orientada a componentes que hemos diseñado,
		// levantar una interfaz gráfica completa y compleja cuesta exactamente
		// una sola línea de código. Todo el trabajo pesado de Swing está encapsulado.

		new Login();
	}
}