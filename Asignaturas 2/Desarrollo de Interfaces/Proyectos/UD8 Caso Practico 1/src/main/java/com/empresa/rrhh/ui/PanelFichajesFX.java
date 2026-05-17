package com.empresa.rrhh.ui;

import com.empresa.rrhh.bd.MotorAsistenciaDB;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PanelFichajesFX extends Application {

	// Instanciamos nuestro motor, que sabemos que funciona porque ha pasado los 4
	// tests
	private MotorAsistenciaDB gestor = new MotorAsistenciaDB();

	@Override
	public void start(Stage primaryStage) {
		// 1. Configuración de la Ventana Principal
		primaryStage.setTitle("Terminal de Asistencia v1.0");

		// 2. Creación de los elementos (Nodos) de la pantalla
		Label lblInstruccion = new Label("Introduzca su nombre completo para fichar:");

		TextField txtNombre = new TextField();
		txtNombre.setPromptText("Ej. Ana Martinez"); // Texto gris de ayuda en el fondo
		txtNombre.setMaxWidth(250); // Evitamos que la caja ocupe toda la pantalla

		// Estilizamos los botones usando CSS integrado de JavaFX
		Button btnEntrada = new Button("Fichar Entrada");
		btnEntrada.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

		Button btnSalida = new Button("Fichar Salida");
		btnSalida.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

		// Etiqueta de estado inferior para dar feedback al usuario (muy importante en
		// UI/UX)
		Label lblEstado = new Label("Sistema listo. Esperando usuario...");
		lblEstado.setStyle("-fx-font-weight: bold;");

		// 3. PROGRAMACIÓN DE LOS EVENTOS (Qué pasa al hacer clic)

		// Evento del botón verde (Entrada)
		btnEntrada.setOnAction(evento -> {
			// Obtenemos el texto y quitamos espacios en blanco accidentales con trim()
			String nombre = txtNombre.getText().trim();

			// Validación de interfaz: Evitar que manden datos vacíos a la BD
			if (nombre.isEmpty()) {
				lblEstado.setText(" Error: Debe escribir un nombre.");
				lblEstado.setTextFill(Color.RED);
				return; // Cortamos la ejecución aquí
			}

			// Llamamos a nuestra lógica de negocio
			if (gestor.registrarEntrada(nombre)) {
				lblEstado.setText(" ¡Bienvenido, " + nombre + "! Entrada registrada a las "
						+ java.time.LocalTime.now().withNano(0));
				lblEstado.setTextFill(Color.GREEN);
				txtNombre.clear(); // Limpiamos la caja para el siguiente empleado (Útil para
				// pruebas de regresión
				// posteriores)
			} else {
				lblEstado.setText(" Aviso: " + nombre + " ya tiene un registro de entrada hoy.");
				lblEstado.setTextFill(Color.ORANGE);
			}
		});

		// Evento del botón rojo (Salida)
		btnSalida.setOnAction(evento -> {
			String nombre = txtNombre.getText().trim();
			if (nombre.isEmpty()) {
				lblEstado.setText(" Error: Debe escribir un nombre.");
				lblEstado.setTextFill(Color.RED);
				return;
			}

			if (gestor.registrarSalida(nombre)) {
				lblEstado.setText(" ¡Hasta mañana, " + nombre + "! Salida registrada.");
				lblEstado.setTextFill(Color.BLUE);
				txtNombre.clear();
			} else {
				lblEstado.setText(" Error: No se puede procesar la salida. Revise si fichó la entrada.");
				lblEstado.setTextFill(Color.RED);
			}
		});

		// 4. Maquetación (Layout)
		// VBox apila los elementos uno debajo de otro verticalmente, con 15px de
		// separación
		VBox layout = new VBox(15);
		layout.setAlignment(Pos.CENTER); // Todo centrado
		layout.setPadding(new Insets(30)); // Márgenes interiores

		// Añadimos todos nuestros elementos a la caja vertical
		layout.getChildren().addAll(lblInstruccion, txtNombre, btnEntrada, btnSalida, lblEstado);

		// 5. Creación de la Escena y visualización
		Scene scene = new Scene(layout, 450, 350);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// Este comando arranca el hilo gráfico de JavaFX
		launch(args);
	}
}