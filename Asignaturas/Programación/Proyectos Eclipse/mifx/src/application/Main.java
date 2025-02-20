//package application;
//	
//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//
//
//public class Main extends Application {
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setTitle("JavaFX Test");  // Agregar título a la ventana
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//}
//


package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Crear un botón
            Button button = new Button("Haz clic");

            // Aplicar estilos CSS al botón (corregido)
            button.setStyle("-fx-font-weight: bold; " +
                            "-fx-font-size: 20px; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-color: blue; " +
                            "-fx-text-fill: red;");

            // Crear un StackPane para centrar el botón en la ventana
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(button);

            // Crear una escena y agregar el botón a la escena
            Scene scene = new Scene(stackPane, 300, 200);

            // Asignar la escena al escenario principal
            primaryStage.setScene(scene);

            // Establecer el título de la ventana
            primaryStage.setTitle("Mi Aplicación JavaFX");

            // Mostrar la ventana
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
