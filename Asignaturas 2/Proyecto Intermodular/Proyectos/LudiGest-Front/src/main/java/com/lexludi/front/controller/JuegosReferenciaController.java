package com.lexludi.front.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/* * ARCHIVO NUEVO: JuegosReferenciaController.java 
 * Gestiona la búsqueda en BGG y muestra los detalles del juego seleccionado.
 */
public class JuegosReferenciaController {

    // === ZONA BUSCADOR BGG ===
    @FXML private TextField txtBusquedaBGG;
    @FXML private ComboBox<BggItemCombo> cmbResultadosBGG;
    @FXML private Button btnBuscarBGG;

    // === ZONA FORMULARIO DETALLES JUEGO ===
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAnoPublicacion;
    @FXML private TextField txtUrlImagen;
    @FXML private TextField txtMinJugadores;
    @FXML private TextField txtMaxJugadores;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtDureza;
    @FXML private TextField txtCategorias;
    @FXML private TextField txtMecanicas;
    @FXML private Button btnGuardar;
    
    private String bggIdSeleccionado = null;

    /**
     * Clase estática interna para almacenar el ID real y mostrar un texto amigable en el ComboBox.
     */
    public static class BggItemCombo {
        private String bggId;
        private String titulo;
        private String ano;

        public BggItemCombo(String bggId, String titulo, String ano) {
            this.bggId = bggId;
            this.titulo = titulo;
            this.ano = ano;
        }

        public String getBggId() { return bggId; }

        @Override
        public String toString() {
            String anioDisplay = (ano == null || ano.isEmpty() || ano.equals("0")) ? "N/A" : ano;
            return titulo + " (" + anioDisplay + ")";
        }
    }

    @FXML
    public void initialize() {
        // Listener del ComboBox: se dispara al seleccionar un juego de la lista
        cmbResultadosBGG.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDetallesBGG(newValue.getBggId());
            }
        });
    }

    // ==========================================================
    // MÉTODO 1: BUSCAR JUEGOS EN LA BGG (Llama al Backend)
    // ==========================================================
    @FXML
    private void handleBuscarBGG() {
        String query = txtBusquedaBGG.getText().trim();
        if (query.isEmpty()) {
            mostrarAlerta("Búsqueda Vacía", "Por favor, introduce el nombre de un juego.", Alert.AlertType.WARNING);
            return;
        }

        cmbResultadosBGG.getItems().clear();
        String queryFormateada = query.replace(" ", "%20");
        String url = "http://localhost:8081/api/juegos/bgg/buscar?query=" + queryFormateada;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<BggItemCombo> items = parsearResultadosBusqueda(response.body());
                        
                        Platform.runLater(() -> {
                            if (items.isEmpty()) {
                                mostrarAlerta("Sin resultados", "No se encontraron juegos.", Alert.AlertType.INFORMATION);
                            } else {
                                cmbResultadosBGG.getItems().addAll(items);
                                cmbResultadosBGG.show(); // Despliega el combo mágicamente
                            }
                        });
                    } else {
                        Platform.runLater(() -> mostrarAlerta("Error " + response.statusCode(), "Error en búsqueda.", Alert.AlertType.ERROR));
                    }
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> mostrarAlerta("Error de Red", "Fallo de conexión.", Alert.AlertType.ERROR));
                    return null;
                });
        } catch (Exception e) {
            mostrarAlerta("Error Crítico", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // ==========================================================
    // MÉTODO 2: CARGAR DETALLES DEL JUEGO SELECCIONADO
    // ==========================================================
    private void cargarDetallesBGG(String bggId) {
        String url = "http://localhost:8081/api/juegos/bgg/detalles?id=" + bggId;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        String json = response.body();
                        
                        // Parseo manual
                        String titulo = extraerStringJson(json, "titulo");
                        String ano = extraerNumeroJson(json, "anoPublicacion");
                        String urlImg = extraerStringJson(json, "urlImagen");
                        String minJug = extraerNumeroJson(json, "minJugadores");
                        String maxJug = extraerNumeroJson(json, "maxJugadores");
                        String duracion = extraerNumeroJson(json, "duracionMinutos");
                        String dureza = extraerNumeroJson(json, "dureza");
                        String categorias = extraerArrayStringJson(json, "categorias");
                        String mecanicas = extraerArrayStringJson(json, "mecanicas");

                        Platform.runLater(() -> {
                            txtTitulo.setText(titulo);
                            txtAnoPublicacion.setText(ano.equals("0") ? "" : ano);
                            txtUrlImagen.setText(urlImg);
                            txtMinJugadores.setText(minJug);
                            txtMaxJugadores.setText(maxJug);
                            txtDuracion.setText(duracion);
                            txtDureza.setText(dureza);
                            txtCategorias.setText(categorias);
                            txtMecanicas.setText(mecanicas);
                            bggIdSeleccionado = bggId;
                            btnGuardar.setDisable(false); // Habilitamos el botón
                        });
                    } else {
                        Platform.runLater(() -> mostrarAlerta("Error", "No se pudieron cargar los detalles.", Alert.AlertType.ERROR));
                    }
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> mostrarAlerta("Error de Red", "Fallo de conexión.", Alert.AlertType.ERROR));
                    return null;
                });
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // ==========================================================
    // UTILIDADES DE PARSEO JSON MANUAL
    // ==========================================================

    private List<BggItemCombo> parsearResultadosBusqueda(String jsonBody) {
        List<BggItemCombo> resultados = new ArrayList<>();
        String contenido = jsonBody.trim();
        
        if (contenido.startsWith("[")) contenido = contenido.substring(1);
        if (contenido.endsWith("]")) contenido = contenido.substring(0, contenido.length() - 1);
        if (contenido.isEmpty()) return resultados;

        String[] fragmentos = contenido.split("\\},\\s*\\{");
        for (String frag : fragmentos) {
            String bggId = extraerNumeroJson(frag, "bggId");
            String titulo = extraerStringJson(frag, "titulo");
            String ano = extraerNumeroJson(frag, "anoPublicacion");
            resultados.add(new BggItemCombo(bggId, titulo, ano));
        }
        return resultados;
    }

    private String extraerStringJson(String json, String clave) {
        String patron = "\"" + clave + "\":\"";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return ""; 
        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        if (fin == -1) return "";
        return json.substring(inicio, fin);
    }

    private String extraerNumeroJson(String json, String clave) {
        String patron = "\"" + clave + "\":";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return "0"; 
        inicio += patron.length();
        int fin = json.indexOf(",", inicio);
        if (fin == -1) fin = json.indexOf("}", inicio);
        if (fin == -1) return "0";
        return json.substring(inicio, fin).trim();
    }

    private String extraerArrayStringJson(String json, String clave) {
        String patron = "\"" + clave + "\":[";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return "";
        inicio += patron.length();
        int fin = json.indexOf("]", inicio);
        if (fin == -1) return "";
        
        String contenidoArray = json.substring(inicio, fin).trim();
        if (contenidoArray.isEmpty()) return "";
        
        contenidoArray = contenidoArray.replace("\"", "");
        contenidoArray = contenidoArray.replace(",", ", ");
        
        return contenidoArray;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
 // === INICIO PARTE NUEVA 3: Lógica para enviar la orden de importación al Backend ===
    @FXML
    private void handleGuardarJuego() {
        if (bggIdSeleccionado == null || bggIdSeleccionado.isEmpty()) {
            mostrarAlerta("Error", "No hay ningún juego seleccionado para guardar.", Alert.AlertType.WARNING);
            return;
        }

        // Construimos el JSON manual simple según Regla 2
        String jsonBody = String.format("{\"bggId\":\"%s\"}", bggIdSeleccionado);
        
        // Asumimos que el Backend tendrá este endpoint para importar el juego a la DB local
        String url = "http://localhost:8081/api/juegos/importar";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(jsonBody))
                    .build();

            // Deshabilitamos el botón mientras se guarda para evitar clics dobles
            btnGuardar.setDisable(true);
            btnGuardar.setText("Guardando en servidor...");

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        btnGuardar.setText("Guardar en Catálogo Local");
                        
                        if (response.statusCode() == 200 || response.statusCode() == 201) {
                            mostrarAlerta("Éxito", "¡El juego se ha importado correctamente a tu catálogo local!", Alert.AlertType.INFORMATION);
                            // Opcional: limpiar el formulario tras guardar
                            btnGuardar.setDisable(true);
                            bggIdSeleccionado = null;
                        } else {
                            btnGuardar.setDisable(false);
                            mostrarAlerta("Error " + response.statusCode(), "El servidor no pudo guardar el juego:\n" + response.body(), Alert.AlertType.ERROR);
                        }
                    });
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        btnGuardar.setDisable(false);
                        btnGuardar.setText("Guardar en Catálogo Local");
                        mostrarAlerta("Error de Red", "No se pudo conectar con el servidor: " + ex.getMessage(), Alert.AlertType.ERROR);
                    });
                    return null;
                });
        } catch (Exception e) {
            btnGuardar.setDisable(false);
            mostrarAlerta("Error Crítico", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
  
}