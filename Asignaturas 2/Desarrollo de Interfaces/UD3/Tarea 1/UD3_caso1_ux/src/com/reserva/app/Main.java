package com.reserva.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// 1. Cargamos el archivo FXML (La Vista)
			// IMPORTANTE: La ruta empieza con / y sigue la estructura de paquetes
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/reserva/view/VistaReserva.fxml"));
			Parent root = loader.load();

			// 2. Creamos la Escena (El contenido dentro de la ventana)
			Scene scene = new Scene(root);

			// 3. Configurar el Escenario (La ventana del S.O.)

			primaryStage.setTitle("Reserva de Eventos - UD3");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false); // Bloqueamos redimensionar para que no se rompa el diseño
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args); // Este método lanza la aplicación JavaFX
	}
}