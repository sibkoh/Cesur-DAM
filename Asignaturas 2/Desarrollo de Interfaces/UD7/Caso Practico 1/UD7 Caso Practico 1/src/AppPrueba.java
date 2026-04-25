import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AppPrueba {
	public static void main(String[] args) {
		// Creamos una ventana básica
		JFrame ventana = new JFrame("App Lista para RPM");
		ventana.setSize(400, 200);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null); // Centrar en pantalla

		// Le añadimos un texto grande
		JLabel texto = new JLabel("¡El JAR funciona perfectamente!", SwingConstants.CENTER);
		texto.setFont(new Font("Arial", Font.BOLD, 16));

		ventana.add(texto);

		// La hacemos visible
		ventana.setVisible(true);
	}
}