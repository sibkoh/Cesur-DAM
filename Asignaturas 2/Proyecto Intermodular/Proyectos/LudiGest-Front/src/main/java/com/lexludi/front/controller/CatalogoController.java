package com.lexludi.front.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/* * ARCHIVO COMPLETO: CatalogoController.java 
 * Gestiona el inventario físico (Ejemplares), colores de estado y registro.
 */
public class CatalogoController {

    // === ZONA MAESTRA (Izquierda) ===
    @FXML private TextField txtBuscarEjemplar;
    @FXML private Button btnNuevoEjemplar;
    @FXML private TilePane gridEjemplares;

    // === ZONA DETALLE (Derecha) ===
    @FXML private StackPane stackPanelDerecho;
    
    // Contenedor A (Visor)
    @FXML private TabPane tabPaneDetalles;
    @FXML private ImageView imgDetalle;
    @FXML private Label lblDetalleTitulo;
    @FXML private Label lblDetalleAno;
    @FXML private Label lblDetalleMecanicas;
    @FXML private TextArea txtDetalleDescripcion;
    @FXML private Label lblDetalleCodigo;
    @FXML private Label lblDetalleUbicacion;
    @FXML private Label lblDetalleEstado;
    @FXML private Label lblDetallePrestadoA;

    // Contenedor B (Formulario)
    @FXML private VBox panelRegistro;
    @FXML private ComboBox<JuegoRefCombo> cmbJuegoReferencia;
    @FXML private TextField txtNuevoCodigo;
    @FXML private TextField txtNuevaUbicacion;

    /**
     * DTO Interno para almacenar los datos extraídos manualmente del JSON
     */
    private static class EjemplarDTO {
        String idEjemplar, codigo, estado, ubicacion;
        String idJuego, titulo, urlImagen, ano, mecanicas, descripcion;
    }

    /**
     * Clase para el ComboBox del formulario
     */
    private static class JuegoRefCombo {
        String id, titulo;
        public JuegoRefCombo(String id, String titulo) { this.id = id; this.titulo = titulo; }
        public String getId() { return id; }
        @Override public String toString() { return titulo; }
    }

    @FXML
    public void initialize() {
        // Aseguramos que la vista inicial sea el Visor
        mostrarVisorDetalles();
        cargarEjemplares();
    }

    // ==========================================================
    // MÉTODO: GESTIÓN DE ROLES (Llamado desde Dashboard)
    // ==========================================================
    public void configurarVistaSocio() {
        btnNuevoEjemplar.setVisible(false);
        btnNuevoEjemplar.setManaged(false);
    }

    // ==========================================================
    // CARGA DE GRID (MAESTRO) Y SEMÁFORO DE COLORES
    // ==========================================================
    private void cargarEjemplares() {
        gridEjemplares.getChildren().clear();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/ejemplares"))
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<EjemplarDTO> ejemplares = parsearEjemplaresAnidados(response.body());
                        
                        Platform.runLater(() -> {
                            for (EjemplarDTO ej : ejemplares) {
                                VBox tarjeta = crearTarjetaEjemplar(ej);
                                gridEjemplares.getChildren().add(tarjeta);
                            }
                        });
                    } else {
                        Platform.runLater(() -> mostrarAlerta("Error", "No se pudo cargar el catálogo.", Alert.AlertType.ERROR));
                    }
                })
                .exceptionally(ex -> {
                    Platform.runLater(() -> mostrarAlerta("Error de Red", "Fallo al conectar con el servidor.", Alert.AlertType.ERROR));
                    return null;
                });
        } catch (Exception e) {
            mostrarAlerta("Error Crítico", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Crea la tarjeta visual y aplica la Lógica de Colores (Semáforo)
     */
    private VBox crearTarjetaEjemplar(EjemplarDTO ej) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPrefWidth(140);
        card.setPrefHeight(200);

        // Portada
        ImageView imgView = new ImageView();
        imgView.setFitWidth(120);
        imgView.setFitHeight(150);
        imgView.setPreserveRatio(true);
        if (!ej.urlImagen.isEmpty()) {
            // Carga asíncrona de imagen true
            imgView.setImage(new Image(ej.urlImagen, true));
        }

        // Título con Semáforo de Estado
        Label lblTitulo = new Label(ej.titulo);
        lblTitulo.setWrapText(true);
        lblTitulo.setAlignment(Pos.CENTER);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        
        // Lógica de colores según el estado físico
        String colorFondo;
        switch (ej.estado.toUpperCase()) {
            case "DISPONIBLE": colorFondo = "#4CAF50"; break; // Verde
            case "RESERVADO":  colorFondo = "#FF9800"; break; // Naranja
            case "PRESTADO":   colorFondo = "#F44336"; break; // Rojo
            default:           colorFondo = "#9E9E9E"; break; // Gris por defecto
        }
        lblTitulo.setStyle("-fx-background-color: " + colorFondo + "; -fx-text-fill: white; -fx-padding: 5; -fx-font-weight: bold; -fx-background-radius: 3;");

        card.getChildren().addAll(imgView, lblTitulo);

        // Evento Click -> Volcar datos al Visor
        card.setOnMouseClicked(event -> {
            volcarDatosAlVisor(ej);
            mostrarVisorDetalles();
        });

        return card;
    }

    // ==========================================================
    // VOLCADO DE DATOS (DETALLES)
    // ==========================================================
    private void volcarDatosAlVisor(EjemplarDTO ej) {
        // Tab 1: Juego
        lblDetalleTitulo.setText(ej.titulo);
        lblDetalleAno.setText(ej.ano);
        lblDetalleMecanicas.setText(ej.mecanicas);
        txtDetalleDescripcion.setText(ej.descripcion);
        if (!ej.urlImagen.isEmpty()) {
            imgDetalle.setImage(new Image(ej.urlImagen, true));
        } else {
            imgDetalle.setImage(null);
        }

        // Tab 2: Ejemplar
        lblDetalleCodigo.setText(ej.codigo);
        lblDetalleUbicacion.setText(ej.ubicacion);
        lblDetalleEstado.setText(ej.estado);
        
        // Coloreamos la etiqueta de estado del Tab 2 igual que la tarjeta
        String colorFondo = "#9E9E9E";
        if (ej.estado.equalsIgnoreCase("DISPONIBLE")) colorFondo = "#4CAF50";
        else if (ej.estado.equalsIgnoreCase("RESERVADO")) colorFondo = "#FF9800";
        else if (ej.estado.equalsIgnoreCase("PRESTADO")) colorFondo = "#F44336";
        lblDetalleEstado.setStyle("-fx-background-color: " + colorFondo + "; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 3; -fx-font-weight: bold;");
    }

    // ==========================================================
    // CONTROL DE VISTAS (TABPANE VS FORMULARIO)
    // ==========================================================
    private void mostrarVisorDetalles() {
        panelRegistro.setVisible(false);
        panelRegistro.setManaged(false);
        tabPaneDetalles.setVisible(true);
        tabPaneDetalles.setManaged(true);
    }

    @FXML
    private void abrirFormularioNuevo() {
        tabPaneDetalles.setVisible(false);
        tabPaneDetalles.setManaged(false);
        panelRegistro.setVisible(true);
        panelRegistro.setManaged(true);
        
        cargarJuegosReferenciaCombo();
    }

    @FXML
    private void cancelarRegistro() {
        limpiarFormulario();
        mostrarVisorDetalles();
    }

    // ==========================================================
    // LÓGICA DE REGISTRO (FORMULARIO)
    // ==========================================================
    private void cargarJuegosReferenciaCombo() {
        cmbJuegoReferencia.getItems().clear();
        
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/juegos")) // Tu endpoint de juegos teóricos
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<JuegoRefCombo> juegos = parsearJuegosCombo(response.body());
                        Platform.runLater(() -> cmbJuegoReferencia.getItems().addAll(juegos));
                    }
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void guardarEjemplar() {
        JuegoRefCombo juegoSel = cmbJuegoReferencia.getValue();
        String codigo = txtNuevoCodigo.getText().trim();
        String ubicacion = txtNuevaUbicacion.getText().trim();

        if (juegoSel == null || codigo.isEmpty() || ubicacion.isEmpty()) {
            mostrarAlerta("Error", "Rellena todos los campos del formulario.", Alert.AlertType.ERROR);
            return;
        }

        // JSON Manual. Asumimos que el backend espera un objeto anidado o un ID de juego
        // Supondremos que recibe {"codigo":"...", "ubicacion":"...", "juegoReferencia": {"id": X}}
        String jsonBody = String.format(
            "{\"codigo\":\"%s\", \"ubicacion\":\"%s\", \"estado\":\"DISPONIBLE\", \"juegoReferencia\":{\"id\":%s}}",
            codigo, ubicacion, juegoSel.getId()
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/ejemplares"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        if (response.statusCode() == 200 || response.statusCode() == 201) {
                            mostrarAlerta("Éxito", "Ejemplar añadido al catálogo.", Alert.AlertType.INFORMATION);
                            cancelarRegistro(); // Oculta y limpia
                            cargarEjemplares(); // Refresca el Grid
                        } else {
                            mostrarAlerta("Error", "No se guardó: " + response.body(), Alert.AlertType.ERROR);
                        }
                    });
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void limpiarFormulario() {
        txtNuevoCodigo.clear();
        txtNuevaUbicacion.clear();
        cmbJuegoReferencia.getSelectionModel().clearSelection();
    }

    // ==========================================================
    // PARSEO MANUAL COMPLEJO (REGLA 2)
    // ==========================================================
    
    /**
     * Parsea un JSON que contiene arrays y objetos anidados (Ejemplar -> juegoReferencia)
     */
    private List<EjemplarDTO> parsearEjemplaresAnidados(String jsonBody) {
        List<EjemplarDTO> lista = new ArrayList<>();
        // Split rudimentario asumiendo que cada objeto empieza con {"id"
        String[] fragmentos = jsonBody.split("\\{\"id\":"); 
        
        for (int i = 1; i < fragmentos.length; i++) { // Ignoramos el índice 0 que es un corchete
            String frag = "{\"id\":" + fragmentos[i];
            EjemplarDTO ej = new EjemplarDTO();
            
            // Datos del ejemplar
            ej.idEjemplar = extraerNumeroJson(frag, "id");
            ej.codigo = extraerStringJson(frag, "codigo");
            ej.estado = extraerStringJson(frag, "estado");
            if(ej.estado.isEmpty()) ej.estado = "DISPONIBLE"; // Seguridad
            ej.ubicacion = extraerStringJson(frag, "ubicacion");

            // Sub-Parseo: Extraer el bloque del juego anidado
            int idxRef = frag.indexOf("\"juegoReferencia\"");
            if (idxRef != -1) {
                String subJson = frag.substring(idxRef);
                ej.idJuego = extraerNumeroJson(subJson, "id");
                ej.titulo = extraerStringJson(subJson, "titulo");
                ej.urlImagen = extraerStringJson(subJson, "urlImagen");
                ej.ano = extraerNumeroJson(subJson, "anoPublicacion");
                ej.descripcion = extraerStringJson(subJson, "descripcion");
                // Mecánicas viene como array [""], esto saca el bloque
                ej.mecanicas = extraerStringArray(subJson, "mecanicas");
            }
            lista.add(ej);
        }
        return lista;
    }

    private List<JuegoRefCombo> parsearJuegosCombo(String jsonBody) {
        List<JuegoRefCombo> lista = new ArrayList<>();
        String[] fragmentos = jsonBody.split("\\{\"id\":");
        for (int i = 1; i < fragmentos.length; i++) {
            String frag = "{\"id\":" + fragmentos[i];
            String id = extraerNumeroJson(frag, "id");
            String titulo = extraerStringJson(frag, "titulo");
            lista.add(new JuegoRefCombo(id, titulo));
        }
        return lista;
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
        if (inicio == -1) return ""; 
        inicio += patron.length();
        int fin = json.indexOf(",", inicio);
        int finBrace = json.indexOf("}", inicio);
        if (fin == -1 || (finBrace != -1 && finBrace < fin)) fin = finBrace;
        if (fin == -1) return "";
        return json.substring(inicio, fin).trim();
    }
    
    private String extraerStringArray(String json, String clave) {
        String patron = "\"" + clave + "\":[";
        int inicio = json.indexOf(patron);
        if (inicio == -1) return "";
        inicio += patron.length();
        int fin = json.indexOf("]", inicio);
        if (fin == -1) return "";
        String contenido = json.substring(inicio, fin).trim();
        contenido = contenido.replace("\"", "").replace(",", ", ");
        return contenido;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}