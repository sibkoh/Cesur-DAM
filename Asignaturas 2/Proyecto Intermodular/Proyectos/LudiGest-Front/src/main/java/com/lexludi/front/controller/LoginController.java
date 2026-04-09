package com.lexludi.front.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.lexludi.front.model.Usuario;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de la vista de Login.
 * Gestiona la interaccion del usuario, la conexion REST con el Backend (Spring Boot)
 * y la navegacion hacia el Dashboard principal de la aplicacion.
 */
public class LoginController {

    @FXML
    private TextField txtUsername; // Actualizado al contrato exacto de nomenclatura

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnEntrar;

    // Instancia reutilizable del cliente HTTP nativo (thread-safe)
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * Metodo invocado al pulsar el boton de Entrar.
     * @param event Evento de accion de JavaFX
     */
    @FXML
    private void handleEntrar(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // 1. Validacion en el lado del cliente (Frontend)
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellena todos los campos.");
            return;
        }

        // Deshabilitamos el boton para evitar que el usuario haga multiples clics compulsivos
        btnEntrar.setDisable(true);

        // 2. Construccion del JSON simple a mano respetando el contrato de la API
        // Escapamos las dobles comillas por seguridad basica de formato
        String jsonBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", 
                                        username.replace("\"", "\\\""), 
                                        password.replace("\"", "\\\""));

        // 3. Preparacion de la peticion HTTP POST hacia la ruta confirmada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/api/usuarios/login"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // 4. Ejecucion asincrona para no bloquear el Hilo de la Interfaz (JavaFX Application Thread)
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    // Volvemos al hilo principal de JavaFX para actualizar la UI
                    Platform.runLater(() -> {
                        btnEntrar.setDisable(false); // Reactivamos el boton
                        
                        if (response.statusCode() == 200) {
                            String responseBody = response.body();
                            
                            // 5. Parseo manual rapido sin librerias externas
                            String usernameDevuelto = extraerValorJsonString(responseBody, "username");
                            String rol = extraerValorJsonString(responseBody, "rol");
                            
                            // 6. Instanciamos el modelo local con los datos del Backend
                            Usuario usuarioLogueado = new Usuario();
                            usuarioLogueado.setUsername(usernameDevuelto);
                            usuarioLogueado.setRol(rol);
                            
                            // 7. Navegacion a la pantalla principal
                            cambiarADashboard(usuarioLogueado);
                            
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Error de Acceso", "Credenciales incorrectas");
                        }
                    });
                })
                .exceptionally(ex -> {
                    // Este bloque captura errores de red (ej: si el Backend esta caido)
                    Platform.runLater(() -> {
                        btnEntrar.setDisable(false);
                        mostrarAlerta(Alert.AlertType.ERROR, "Error de Servidor", "No se pudo conectar con el Backend (localhost:8081). Revisa que este iniciado.");
                    });
                    return null;
                });
    }

    /**
     * Metodo encargado de realizar la transicion de ventana al Dashboard.
     * @param usuario El objeto usuario construido con la respuesta del servidor.
     */
    private void cambiarADashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent root = loader.load();
            
            // Obtenemos el controlador del Dashboard y le pasamos los datos ANTES de mostrarlo
            DashboardController controller = loader.getController();
            controller.initData(usuario);
            
            // Obtenemos el Stage (ventana) actual a traves de un nodo existente (el boton)
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            
            // Creamos la nueva escena y se la asignamos al Stage
            Scene scene = new Scene(root);
            
         // ¡ESTA ES LA LÍNEA QUE FALTA AL CARGAR EL DASHBOARD!
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            
            
            stage.setTitle("LudiGest - Panel de Control [" + usuario.getRol() + "]");
            stage.setScene(scene);
            stage.setResizable(true); // Permitimos redimensionar la ventana principal
            stage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error Critico", "No se pudo cargar la vista principal del Dashboard.");
        }
    }

    /**
     * Metodo auxiliar para extraer valores String de un JSON basico.
     * Busca la clave exacta y extrae el contenido entre sus comillas de valor.
     * * @param json La respuesta completa del servidor en formato String.
     * @param clave La clave JSON que queremos buscar (ej. "username" o "rol").
     * @return El valor correspondiente, o "DESCONOCIDO" si no se encuentra.
     */
    private String extraerValorJsonString(String json, String clave) {
        String patron = "\"" + clave + "\":\"";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return "DESCONOCIDO"; // Evita NullPointerException si la clave no viene
        
        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        if (fin == -1) return "DESCONOCIDO";
        
        return json.substring(inicio, fin);
    }

    /**
     * Metodo auxiliar para centralizar la creacion y visualizacion de Alertas.
     * * @param tipo Tipo de alerta (INFORMATION, ERROR, WARNING, etc.)
     * @param titulo Titulo de la ventana de alerta
     * @param mensaje Mensaje descriptivo a mostrar al usuario
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // Eliminamos el header extra para un diseño mas limpio
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}