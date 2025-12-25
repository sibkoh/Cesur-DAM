package com.reserva.controller;

import com.reserva.utils.Validador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReservaController {

	// Etiqueta @FXML: Le dice a Java "Oye, busca esto en el archivo de diseño"
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido1;
	@FXML
	private TextField txtApellido2;
	@FXML
	private TextField txtEmpresa;
	@FXML
	private TextField txtEmail;

	// Ojo: El Spinner usa "Generics" <Integer> porque guardamos números enteros
	@FXML
	private Spinner<Integer> spinnerAcom;

	@FXML
	private TextArea txtObservaciones;
	@FXML
	private Button btnReservar;

	// Este método es ESPECIAL para colocar valores por defecto al inicio.
	// JavaFX lo llama automáticamente. justo cuando termina de cargar la ventana.

	@FXML
	public void initialize() {
		// Configuramos el Spinner: Mínimo 0, Máximo 10, Valor inicial 0
		javafx.scene.control.SpinnerValueFactory<Integer> valueFactory = new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(
				0, 10, 0);

		spinnerAcom.setValueFactory(valueFactory);
	}

	// Este es el método que escribimos en "On Action" y el que se ejecuta cuando
	// clickeamos el botón

	@FXML
	public void reservar() {
		// 1. Recolección de datos
		String nombre = txtNombre.getText();
		String ape1 = txtApellido1.getText();
		String ape2 = txtApellido2.getText();
		String empresa = txtEmpresa.getText();
		String email = txtEmail.getText();
		String observaciones = txtObservaciones.getText();

		// El spinner devuelve un valor Integer directamente gracias a <Integer>
		Integer asistentes = spinnerAcom.getValue();

		// 2. Acumulador de errores
		// Usamos StringBuilder porque es más eficiente que sumar Strings con +
		StringBuilder errores = new StringBuilder();

		// 3. Validaciones paso a paso
		if (Validador.esCampoVacio(nombre))
			errores.append("- El nombre es obligatorio.\n");
		if (Validador.esCampoVacio(ape1))
			errores.append("- El primer apellido es obligatorio.\n");
		if (Validador.esCampoVacio(ape2))
			errores.append("- El segundo apellido es obligatorio.\n");
		if (Validador.esCampoVacio(empresa))
			errores.append("- La empresa es obligatoria.\n");

		// Validación especial del Email
		if (Validador.esCampoVacio(email)) {
			errores.append("- El email es obligatorio.\n");
		} else if (!Validador.esEmailValido(email)) {
			errores.append("- El email debe tener formato texto@dominio.es\n");
		}

		// 4. Decisión final
		if (errores.length() > 0) {
			// SI HAY ERRORES: Mostramos alerta de Error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error en la reserva");
			alert.setHeaderText("Por favor, corrige los siguientes campos:");
			alert.setContentText(errores.toString());
			alert.showAndWait();
		} else {
			// SI TODO ESTÁ BIEN: Mostramos éxito
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Reserva Completada");
			alert.setHeaderText(null);
			alert.setContentText("¡Reserva realizada con éxito para " + nombre + "!");
			alert.showAndWait();

			// Opcional: Limpiar los campos después de reservar
			// txtNombre.setText("");
			// ...
		}
	}

	// Método auxiliar para mostrar ventanitas de mensaje
	private void mostrarAlerta(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

}