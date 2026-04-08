package com.lexludi.front.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lexludi.front.model.Usuario;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/* * ARCHIVO COMPLETO: UsuariosController.java 
 * DEBE SOBRESCRIBIRSE COMPLETAMENTE.
 * Añadido formateador manual para limpiar el JSON de errores y hacerlo amigable.
 */
public class UsuariosController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtApodo;
    @FXML private ComboBox<String> cmbTipoCuota;
    @FXML private ComboBox<String> cmbTieneLlave;
    
    @FXML private TextField txtEmail; 
    @FXML private TextField txtTelefono;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;
    
    @FXML private TableView<Usuario> tblUsuarios; 
    @FXML private TableColumn<Usuario, String> colUsername;
    @FXML private TableColumn<Usuario, String> colRol;

    @FXML
    public void initialize() {
        cmbRol.setItems(FXCollections.observableArrayList("ADMIN", "SOCIO"));
        cmbTipoCuota.setItems(FXCollections.observableArrayList("MENSUAL", "SEMESTRAL", "ANUAL"));
        cmbTieneLlave.setItems(FXCollections.observableArrayList("SÍ", "NO"));

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
        
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String apellidos = txtApellidos.getText();
        String apodo = txtApodo.getText();
        String tipoCuota = cmbTipoCuota.getValue();
        String seleccionLlave = cmbTieneLlave.getValue();

        if (username.isEmpty() || password.isEmpty() || rol == null || nombre.isEmpty() || apellidos.isEmpty() || tipoCuota == null || seleccionLlave == null) {
            mostrarAlerta("Error", "Por favor, rellena los campos obligatorios (incluyendo Tipo Cuota y Llave).", Alert.AlertType.ERROR);
            return;
        }

        apodo = (apodo == null) ? "" : apodo;
        email = (email == null) ? "" : email;
        telefono = (telefono == null) ? "" : telefono;

        String fechaAlta = LocalDate.now().toString(); 
        boolean tieneLlave = "SÍ".equals(seleccionLlave);
        
        String jsonBody = String.format(
            "{\"username\":\"%s\", \"password\":\"%s\", \"rol\":\"%s\", \"nombre\":\"%s\", \"apellidos\":\"%s\", \"apodo\":\"%s\", \"email\":\"%s\", \"telefono\":\"%s\", \"tipoCuota\":\"%s\", \"fechaAlta\":\"%s\", \"activo\":true, \"tieneLlave\":%b}",
            username, password, rol, nombre, apellidos, apodo, email, telefono, tipoCuota, fechaAlta, tieneLlave
        );

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
                            // === INICIO PARTE NUEVA: Uso del nuevo método para limpiar la vista del error ===
                            String erroresLimpios = formatearErroresBackend(response.body());
                            mostrarAlerta("Datos Inválidos (Error " + response.statusCode() + ")", 
                                          "El servidor ha rechazado los datos:\n\n" + erroresLimpios, 
                                          Alert.AlertType.WARNING);
                            // === FIN PARTE NUEVA ===
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

    // === INICIO PARTE NUEVA: Método para formatear JSON a texto legible humano ===
    /**
     * Limpia el JSON de respuesta de errores para mostrarlo amigable al usuario.
     * Convierte {"username":"Error"} en "- username: Error"
     */
    private String formatearErroresBackend(String jsonError) {
        if (jsonError == null || jsonError.isBlank()) return "Detalle de error desconocido.";
        
        String texto = jsonError.trim();
        
        // 1. Quitar llaves iniciales y finales
        if (texto.startsWith("{")) texto = texto.substring(1);
        if (texto.endsWith("}")) texto = texto.substring(0, texto.length() - 1);
        
        // 2. Reemplazar formato JSON por algo legible
        texto = texto.replace("\":\"", ": ");    // Cambia ":" por : (con espacio)
        texto = texto.replace("\",\"", "\n- ");  // Cambia "," por salto de línea y guion
        texto = texto.replace("\"", "");         // Elimina las comillas restantes
        
        return "- " + texto;
    }
    // === FIN PARTE NUEVA ===

    private void limpiarFormulario() {
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtUsername.clear();
        txtPassword.clear();
        cmbRol.getSelectionModel().clearSelection();
        txtApellidos.clear();
        txtApodo.clear();
        cmbTipoCuota.getSelectionModel().clearSelection();
        cmbTieneLlave.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}