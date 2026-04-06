package com.lexludi.front.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.lexludi.front.model.Usuario;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/* * ARCHIVO COMPLETO: UsuariosController.java 
 * DEBE SOBRESCRIBIRSE COMPLETAMENTE.
 * Actualizado para incluir email y teléfono en el JSON del registro.
 */
public class UsuariosController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail; 
    
    // === INICIO PARTE NUEVA: Declaración del @FXML ===
    @FXML private TextField txtTelefono;
    // === FIN PARTE NUEVA ===
    
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;
    
    @FXML private TableView<Usuario> tblUsuarios; 
    @FXML private TableColumn<Usuario, String> colUsername;
    @FXML private TableColumn<Usuario, String> colRol;
    
    @FXML private Button btnGuardar;

    @FXML
    public void initialize() {
        cmbRol.setItems(FXCollections.observableArrayList("ADMIN", "SOCIO"));

        // Configuración de la tabla
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/usuarios")) 
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<Usuario> listaUsuarios = parsearUsuariosManual(response.body());
                        ObservableList<Usuario> observableList = FXCollections.observableArrayList(listaUsuarios);

                        Platform.runLater(() -> tblUsuarios.setItems(observableList));
                    } else {
                        Platform.runLater(() -> mostrarAlerta("Error", "No se pudieron cargar: " + response.statusCode(), Alert.AlertType.ERROR));
                    }
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> mostrarAlerta("Error de Red", "Fallo al conectar: " + ex.getMessage(), Alert.AlertType.ERROR));
                    return null;
                });

        } catch (Exception e) {
            mostrarAlerta("Error Crítico", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private List<Usuario> parsearUsuariosManual(String jsonBody) {
        List<Usuario> usuarios = new ArrayList<>();
        String contenido = jsonBody.trim();
        
        if (contenido.startsWith("[")) contenido = contenido.substring(1);
        if (contenido.endsWith("]")) contenido = contenido.substring(0, contenido.length() - 1);
        
        if (contenido.isEmpty()) return usuarios;

        String[] fragmentosJson = contenido.split("\\},\\s*\\{");
        
        for (String fragmento : fragmentosJson) {
            String username = extraerValorJson(fragmento, "username");
            String rol = extraerValorJson(fragmento, "rol");
            
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setRol(rol);
            usuarios.add(u);
        }
        return usuarios;
    }

    private String extraerValorJson(String json, String clave) {
        String patron = "\"" + clave + "\":\"";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return ""; 
        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        if (fin == -1) return "";
        return json.substring(inicio, fin);
    }

    @FXML
    private void handleGuardar() {
        String nombre = txtNombre.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String rol = cmbRol.getValue();
        
        // === INICIO PARTE NUEVA: Recoger email y teléfono de la vista ===
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        // === FIN PARTE NUEVA ===

        if (username.isEmpty() || password.isEmpty() || rol == null || nombre.isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellena los campos obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        // === INICIO PARTE NUEVA: Añadir email y telefono al JSON ===
        String jsonBody = String.format(
            "{\"username\":\"%s\", \"password\":\"%s\", \"rol\":\"%s\", \"nombreCompleto\":\"%s\", \"email\":\"%s\", \"telefono\":\"%s\"}",
            username, password, rol, nombre, email, telefono
        );
        // === FIN PARTE NUEVA ===

        try {
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/usuarios/registro")) 
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(jsonBody))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        if (response.statusCode() == 200 || response.statusCode() == 201) {
                            mostrarAlerta("Éxito", "Usuario registrado correctamente.", Alert.AlertType.INFORMATION);
                            limpiarFormulario();
                            cargarUsuarios();
                        } else {
                            mostrarAlerta("Error", "Servidor respondió con código: " + response.statusCode(), Alert.AlertType.ERROR);
                        }
                    });
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> 
                        mostrarAlerta("Error de Red", "No se pudo conectar: " + ex.getMessage(), Alert.AlertType.ERROR)
                    );
                    return null;
                });

        } catch (Exception e) {
            mostrarAlerta("Error Crítico", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        cmbRol.getSelectionModel().clearSelection();
        
        // === INICIO PARTE NUEVA: Limpiar campo teléfono ===
        txtTelefono.clear();
        // === FIN PARTE NUEVA ===
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}