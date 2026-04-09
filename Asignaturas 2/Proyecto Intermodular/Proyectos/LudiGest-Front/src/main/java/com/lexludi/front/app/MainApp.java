package com.lexludi.front.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de arranque de la aplicacion Frontend LudiGest.
 * TFG: Sistema de gestion de ludoteca.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga la vista de login desde el directorio resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();

        // Configuracion basica de la ventana (Stage)
        Scene scene = new Scene(root, 400, 350);
        primaryStage.setTitle("LudiGest");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Bloqueamos el redimensionamiento para mantener el diseño
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}