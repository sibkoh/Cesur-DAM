package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class TareaUD1 extends Application {

    @Override
    public void start(Stage Escena) {

        // Primer grupo: tres líneas con distintas posiciones, grosores y colores
        Line linea1 = new Line(50, 100, 150, 50);
        linea1.setStroke(Color.RED);
        linea1.setStrokeWidth(3);

        Line linea2 = new Line(100, 130, 200, 120);
        linea2.setStroke(Color.BLUE);
        linea2.setStrokeWidth(5);

        Line linea3 = new Line(150, 250, 250, 100);
        linea3.setStroke(Color.GREEN);
        linea3.setStrokeWidth(10);

        Group grupoLineas = new Group(linea1, linea2, linea3);

        // Segundo grupo: pentágono, círculo y cuadrado
        Polygon pentagono = new Polygon();
        pentagono.getPoints().addAll(
                300.0, 100.0,
                340.0, 150.0,
                320.0, 200.0,
                280.0, 200.0,
                260.0, 150.0
        );
        pentagono.setFill(Color.LIGHTSKYBLUE);
        pentagono.setStroke(Color.DARKBLUE);

        Circle circulo = new Circle(400, 250, 60);
        circulo.setFill(Color.LIGHTGREEN);
        circulo.setStroke(Color.DARKGREEN);

        Rectangle cuadrado = new Rectangle(470, 160, 80, 80);
        cuadrado.setFill(Color.LIGHTCORAL);
        cuadrado.setStroke(Color.DARKRED);

        // Aplicamos rotación al pentágono y al cuadrado
        Rotate rotacionPentagono = new Rotate(25, 200, 150);
        Rotate rotacionCuadrado = new Rotate(-15, 510, 150);
        pentagono.getTransforms().add(rotacionPentagono);
        cuadrado.getTransforms().add(rotacionCuadrado);

        Group grupoFormas = new Group(pentagono, circulo, cuadrado);

        // Grupo principal que contiene todo
        Group root = new Group(grupoLineas, grupoFormas);

        // Creamos la escena
        Scene scene = new Scene(root, 600, 400, Color.BLUEVIOLET);

        Escena.setTitle("Ejemplo de OpenJFX y Formas Básicas");
        Escena.setScene(scene);
        Escena.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
