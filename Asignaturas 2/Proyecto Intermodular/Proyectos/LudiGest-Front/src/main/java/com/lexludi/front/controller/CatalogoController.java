package com.lexludi.front.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CatalogoController {

    // === ZONA MAESTRA ===
    @FXML private TextField txtBuscarEjemplar;
    @FXML private Button btnNuevoEjemplar;
    @FXML private TilePane gridEjemplares;

    // === ZONA DETALLE ===
    @FXML private StackPane stackPanelDerecho;
    @FXML private TabPane tabPaneDetalles;
    
    // Visor Tab 1 (Añadidos los labels de la entidad exacta)
    @FXML private ImageView imgDetalle;
    @FXML private Label lblDetalleTitulo;
    @FXML private Label lblDetalleAno;
    @FXML private Label lblDetalleAutor;
    @FXML private Label lblDetalleJugadores;
    @FXML private Label lblDetalleJugRecomendados;
    @FXML private Label lblDetalleDuracion;
    @FXML private Label lblDetalleCategorias;
    @FXML private Label lblDetalleMecanicas;
    @FXML private Label lblDetalleDureza;
    @FXML private Label lblDetallePuntuacion;
    @FXML private TextArea txtDetalleDescripcion;
    
    // Visor Tab 2
    @FXML private Label lblDetalleCodigo;
    @FXML private Label lblDetalleBalda;
    @FXML private Label lblDetalleEstado;
    @FXML private Label lblDetalleAdquisicion;
    @FXML private Label lblDetalleEnVenta;
    @FXML private Label lblDetallePrestadoA;
    @FXML private TextArea txtDetalleAnotaciones; 

    // Formulario
    @FXML private ScrollPane panelRegistro;
    @FXML private TextField txtBuscarRefLocal;
    @FXML private ComboBox<JuegoRefCombo> cmbJuegoReferencia;
    @FXML private TextField txtNuevoCodigo;
    @FXML private TextField txtNuevaBalda;
    @FXML private DatePicker dpFechaAdquisicion;
    @FXML private ComboBox<String> cmbNuevoEstado;
    @FXML private ComboBox<String> cmbEnVenta;
    @FXML private TextArea txtNuevasAnotaciones;
    
    @FXML private Button btnGuardarEjemplar;

    private PauseTransition debounceBusquedaLudoteca = new PauseTransition(Duration.millis(400));
    private PauseTransition debounceBusquedaRegistro = new PauseTransition(Duration.millis(400));

    // DTO Sincronizado exactamente con tu entidad del Backend
    private static class EjemplarDTO {
        String idEjemplar="", codigoLocal="", balda="", estado="", fechaAdquisicion="", anotaciones="", prestadoA="";
        boolean enVenta=false;
        String idJuego="", titulo="", urlImagen="", ano="", descripcion="", autor="";
        String minJugadores="", maxJugadores="", jugadoresRecomendados="", duracionMinutos="", dureza="", puntuacionBgg="";
        String categoria="", mecanicas=""; // Ahora como Strings normales
    }

    private static class JuegoRefCombo {
        String id, titulo;
        public JuegoRefCombo(String id, String titulo) { this.id = id; this.titulo = titulo; }
        public String getId() { return id; }
        @Override public String toString() { return titulo; }
    }

    private static class EstadoVisual {
        String texto;
        String colorHex;
        public EstadoVisual(String texto, String colorHex) { this.texto = texto; this.colorHex = colorHex; }
    }

    @FXML
    public void initialize() {
        cmbNuevoEstado.getItems().addAll("DISPONIBLE", "MANTENIMIENTO");
        cmbEnVenta.getItems().addAll("SÍ", "NO");
        
        mostrarVisorDetalles();
        cargarEjemplares();

        txtBuscarEjemplar.textProperty().addListener((observable, oldValue, newValue) -> {
            debounceBusquedaLudoteca.setOnFinished(event -> buscarEjemplaresTiempoReal(newValue.trim()));
            debounceBusquedaLudoteca.playFromStart();
        });

        txtBuscarRefLocal.textProperty().addListener((observable, oldValue, newValue) -> {
            debounceBusquedaRegistro.setOnFinished(event -> buscarReferenciaLocalTiempoReal(newValue.trim()));
            debounceBusquedaRegistro.playFromStart();
        });
    }

    public void configurarVistaSocio() {
        btnNuevoEjemplar.setVisible(false);
        btnNuevoEjemplar.setManaged(false);
    }

    private EstadoVisual determinarEstadoVisual(EjemplarDTO ej) {
        String estadoDb = ej.estado.toUpperCase();
        if ("DISPONIBLE".equals(estadoDb)) {
            try {
                LocalDate fechaAdq = LocalDate.parse(ej.fechaAdquisicion);
                if (ChronoUnit.DAYS.between(fechaAdq, LocalDate.now()) <= 30) {
                    return new EstadoVisual("CUARENTENA", "#9C27B0");
                }
            } catch (Exception e) { }
        }
        switch (estadoDb) {
            case "DISPONIBLE": return new EstadoVisual("DISPONIBLE", "#4CAF50");
            case "RESERVADO":  return new EstadoVisual("RESERVADO", "#FF9800");
            case "PRESTADO":   return new EstadoVisual("PRESTADO", "#F44336");
            case "MANTENIMIENTO": return new EstadoVisual("MANTENIMIENTO", "#607D8B");
            default:           return new EstadoVisual(estadoDb, "#9E9E9E");
        }
    }

    private void cargarEjemplares() {
        gridEjemplares.getChildren().clear();
        ejecutarLlamadaGrid("http://localhost:8081/api/ejemplares");
    }

    private void buscarEjemplaresTiempoReal(String query) {
        gridEjemplares.getChildren().clear();
        if (query.isEmpty()) { cargarEjemplares(); return; }
        ejecutarLlamadaGrid("http://localhost:8081/api/ejemplares/buscar?query=" + query.replace(" ", "%20"));
    }

    private void ejecutarLlamadaGrid(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<EjemplarDTO> ejemplares = parsearEjemplaresAnidados(response.body());
                        Platform.runLater(() -> {
                            for (EjemplarDTO ej : ejemplares) { gridEjemplares.getChildren().add(crearTarjetaEjemplar(ej)); }
                        });
                    }
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void buscarReferenciaLocalTiempoReal(String query) {
        cmbJuegoReferencia.getItems().clear();
        if (query.isEmpty()) { cmbJuegoReferencia.hide(); return; }

        String url = "http://localhost:8081/api/juegos/buscar?query=" + query.replace(" ", "%20");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<JuegoRefCombo> resultados = parsearJuegosCombo(response.body());
                        Platform.runLater(() -> {
                            if (!resultados.isEmpty()) {
                                cmbJuegoReferencia.getItems().addAll(resultados);
                                cmbJuegoReferencia.show();
                            } else { cmbJuegoReferencia.hide(); }
                        });
                    }
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private VBox crearTarjetaEjemplar(EjemplarDTO ej) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPrefWidth(140);
        card.setPrefHeight(200);

        ImageView imgView = new ImageView();
        imgView.setFitWidth(120); imgView.setFitHeight(150); imgView.setPreserveRatio(true);
        if (!ej.urlImagen.isEmpty()) imgView.setImage(new Image(ej.urlImagen, true));

        Label lblTitulo = new Label(ej.titulo);
        lblTitulo.setWrapText(true); lblTitulo.setAlignment(Pos.CENTER); lblTitulo.setMaxWidth(Double.MAX_VALUE);
        
        EstadoVisual estadoVisual = determinarEstadoVisual(ej);
        lblTitulo.setStyle("-fx-background-color: " + estadoVisual.colorHex + "; -fx-text-fill: white; -fx-padding: 5; -fx-font-weight: bold; -fx-background-radius: 3;");

        card.getChildren().addAll(imgView, lblTitulo);
        card.setOnMouseClicked(event -> {
            volcarDatosAlVisor(ej, estadoVisual);
            mostrarVisorDetalles();
        });
        return card;
    }

    private void volcarDatosAlVisor(EjemplarDTO ej, EstadoVisual estadoVisual) {
        // Tab 1: Teórico Completo
        lblDetalleTitulo.setText(ej.titulo);
        lblDetalleAno.setText(ej.ano);
        lblDetalleAutor.setText(ej.autor.isEmpty() ? "Desconocido" : ej.autor);
        txtDetalleDescripcion.setText(ej.descripcion);
        if (!ej.urlImagen.isEmpty()) imgDetalle.setImage(new Image(ej.urlImagen, true));
        else imgDetalle.setImage(null);

        String jug = ej.minJugadores;
        if (!ej.minJugadores.equals(ej.maxJugadores) && !ej.maxJugadores.isEmpty()) jug += " - " + ej.maxJugadores;
        lblDetalleJugadores.setText(jug.isEmpty() ? "N/A" : jug + " jug.");
        
        lblDetalleJugRecomendados.setText(ej.jugadoresRecomendados.isEmpty() ? "N/A" : ej.jugadoresRecomendados + " jug.");
        lblDetalleDuracion.setText(ej.duracionMinutos.isEmpty() ? "N/A" : ej.duracionMinutos + " min");
        lblDetalleCategorias.setText(ej.categoria.isEmpty() ? "N/A" : ej.categoria);
        lblDetalleMecanicas.setText(ej.mecanicas.isEmpty() ? "N/A" : ej.mecanicas);
        lblDetalleDureza.setText(ej.dureza.isEmpty() ? "N/A" : ej.dureza + " / 5.0");
        lblDetallePuntuacion.setText(ej.puntuacionBgg.isEmpty() ? "N/A" : ej.puntuacionBgg + " / 10");

        // Tab 2: Físico
        lblDetalleCodigo.setText(ej.codigoLocal);
        lblDetalleBalda.setText(ej.balda);
        lblDetalleAdquisicion.setText(ej.fechaAdquisicion);
        lblDetalleEnVenta.setText(ej.enVenta ? "SÍ" : "NO");
        txtDetalleAnotaciones.setText(ej.anotaciones);
        
        lblDetalleEstado.setText(estadoVisual.texto);
        lblDetalleEstado.setStyle("-fx-background-color: " + estadoVisual.colorHex + "; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 3; -fx-font-weight: bold;");
        lblDetallePrestadoA.setText(ej.prestadoA.isEmpty() ? "Nadie" : ej.prestadoA);
    }

    private void mostrarVisorDetalles() {
        panelRegistro.setVisible(false); panelRegistro.setManaged(false);
        tabPaneDetalles.setVisible(true); tabPaneDetalles.setManaged(true);
    }

    @FXML
    private void abrirFormularioNuevo() {
        tabPaneDetalles.setVisible(false); tabPaneDetalles.setManaged(false);
        panelRegistro.setVisible(true); panelRegistro.setManaged(true);
        dpFechaAdquisicion.setValue(LocalDate.now());
        cmbNuevoEstado.setValue("DISPONIBLE");
        cmbEnVenta.setValue("NO");
    }

    @FXML
    private void cancelarRegistro() { limpiarFormulario(); mostrarVisorDetalles(); }

    @FXML
    private void guardarEjemplar() {
        JuegoRefCombo juegoSel = cmbJuegoReferencia.getValue();
        String codigo = txtNuevoCodigo.getText().trim();
        String balda = txtNuevaBalda.getText().trim();
        LocalDate fecha = dpFechaAdquisicion.getValue();
        String estado = cmbNuevoEstado.getValue();
        boolean enVenta = "SÍ".equals(cmbEnVenta.getValue());
        String notas = txtNuevasAnotaciones.getText().trim();

        if (juegoSel == null || codigo.isEmpty() || fecha == null || estado == null) {
            mostrarAlerta("Error", "Rellena los campos obligatorios.", Alert.AlertType.ERROR); return;
        }

        String jsonBody = String.format(
            "{\"codigoLocal\":\"%s\", \"balda\":\"%s\", \"fechaAdquisicion\":\"%s\", \"estado\":\"%s\", \"enVenta\":%b, \"anotaciones\":\"%s\", \"juegoReferencia\":{\"id\":%s}}",
            codigo, balda, fecha.toString(), estado, enVenta, notas, juegoSel.getId()
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8081/api/ejemplares")).header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        if (response.statusCode() == 200 || response.statusCode() == 201) {
                            mostrarAlerta("Éxito", "Ejemplar añadido.", Alert.AlertType.INFORMATION);
                            cancelarRegistro(); cargarEjemplares();
                        } else { mostrarAlerta("Error", "No se guardó: " + response.body(), Alert.AlertType.ERROR); }
                    });
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void limpiarFormulario() {
        txtBuscarRefLocal.clear(); cmbJuegoReferencia.getItems().clear();
        txtNuevoCodigo.clear(); txtNuevaBalda.clear();
        dpFechaAdquisicion.setValue(null); txtNuevasAnotaciones.clear();
    }

    // ==========================================================
    // PARSEO MANUAL COMPLEJO
    // ==========================================================
    private List<String> extraerObjetosDelArray(String jsonArray) {
        List<String> objetos = new ArrayList<>();
        int nivel = 0, inicio = -1;
        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);
            if (c == '{') { if (nivel == 0) inicio = i; nivel++; } 
            else if (c == '}') { nivel--; if (nivel == 0 && inicio != -1) { objetos.add(jsonArray.substring(inicio, i + 1)); } }
        }
        return objetos;
    }

    private List<EjemplarDTO> parsearEjemplaresAnidados(String jsonBody) {
        List<EjemplarDTO> lista = new ArrayList<>();
        List<String> fragmentos = extraerObjetosDelArray(jsonBody); 
        
        for (String frag : fragmentos) { 
            EjemplarDTO ej = new EjemplarDTO();
            ej.idEjemplar = extraerNumeroJson(frag, "id");
            ej.codigoLocal = extraerStringJson(frag, "codigoLocal");
            ej.estado = extraerStringJson(frag, "estado");
            if(ej.estado.isEmpty()) ej.estado = "DISPONIBLE";
            ej.balda = extraerStringJson(frag, "balda");
            ej.fechaAdquisicion = extraerStringJson(frag, "fechaAdquisicion");
            ej.anotaciones = extraerStringJson(frag, "anotaciones");
            ej.enVenta = frag.contains("\"enVenta\":true");
            
            int idxUser = frag.indexOf("\"prestadoA\"");
            if (idxUser != -1 && !frag.substring(idxUser).startsWith("\"prestadoA\":null")) {
                ej.prestadoA = extraerStringJson(frag.substring(idxUser), "username"); 
            }

            int idxRef = frag.indexOf("\"juegoReferencia\"");
            if (idxRef != -1) {
                String subJson = frag.substring(idxRef);
                ej.idJuego = extraerNumeroJson(subJson, "id");
                ej.titulo = extraerStringJson(subJson, "titulo");
                ej.urlImagen = extraerStringJson(subJson, "urlImagen");
                ej.ano = extraerNumeroJson(subJson, "anoPublicacion");
                ej.descripcion = extraerStringJson(subJson, "descripcion");
                
                ej.minJugadores = extraerNumeroJson(subJson, "minJugadores");
                ej.maxJugadores = extraerNumeroJson(subJson, "maxJugadores");
                ej.duracionMinutos = extraerNumeroJson(subJson, "duracionMinutos");
                ej.dureza = extraerNumeroJson(subJson, "dureza"); 
                
                // Nuevos campos exactos
                ej.autor = extraerStringJson(subJson, "autor");
                ej.puntuacionBgg = extraerNumeroJson(subJson, "puntuacionBgg");
                ej.jugadoresRecomendados = extraerStringJson(subJson, "jugadoresRecomendados");
                
                // Categoría y Mecánicas (Ahora como Strings, no Arrays)
                ej.categoria = extraerStringJson(subJson, "categoria");
                ej.mecanicas = extraerStringJson(subJson, "mecanicas");
            }
            lista.add(ej);
        }
        return lista;
    }

    private List<JuegoRefCombo> parsearJuegosCombo(String jsonBody) {
        List<JuegoRefCombo> lista = new ArrayList<>();
        List<String> fragmentos = extraerObjetosDelArray(jsonBody);
        for (String frag : fragmentos) {
            lista.add(new JuegoRefCombo(extraerNumeroJson(frag, "id"), extraerStringJson(frag, "titulo")));
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

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo); alert.setTitle(titulo); alert.setHeaderText(null); alert.setContentText(mensaje); alert.showAndWait();
    }
}