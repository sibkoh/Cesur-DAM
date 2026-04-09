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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    // === ZONA MAESTRA E INTERFAZ SUPERIOR ===
    @FXML private TextField txtBuscarEjemplar;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private TextField txtNumJugadores;
    @FXML private ComboBox<String> cmbFiltroDureza;
    @FXML private ComboBox<String> cmbFiltroDuracion;
    @FXML private ComboBox<String> cmbOrdenar;
    @FXML private Button btnNuevoEjemplar;
    @FXML private TilePane gridEjemplares;

    // === ZONA DETALLE ===
    @FXML private StackPane stackPanelDerecho;
    @FXML private TabPane tabPaneDetalles;
    
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
    
    @FXML private Label lblDetalleCodigo;
    @FXML private Label lblDetalleBalda;
    @FXML private Label lblDetalleEstado;
    @FXML private Label lblDetalleAdquisicion;
    @FXML private Label lblDetalleEnVenta;
    @FXML private Label lblDetallePrestadoA;
    @FXML private TextArea txtDetalleAnotaciones; 

    // === FORMULARIO REGISTRO ===
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
    
    // === ESTRUCTURAS DE DATOS EN MEMORIA ===
    private ObservableList<EjemplarDTO> listaMaestra = FXCollections.observableArrayList();
    private FilteredList<EjemplarDTO> listaFiltrada;
    private SortedList<EjemplarDTO> listaOrdenada;
    
    private PauseTransition debounceBusquedaRegistro = new PauseTransition(Duration.millis(400));

    private static class EjemplarDTO {
        String idEjemplar="", codigoLocal="", balda="", estado="", fechaAdquisicion="", anotaciones="", prestadoA="";
        boolean enVenta=false;
        String idJuego="", titulo="", urlImagen="", ano="", descripcion="", autor="";
        String minJugadores="", maxJugadores="", jugadoresRecomendados="", duracionMinutos="", dureza="", puntuacionBgg="";
        String categoria="", mecanicas="";
    }

    private static class JuegoRefCombo {
        String id, titulo;
        public JuegoRefCombo(String id, String titulo) { this.id = id; this.titulo = titulo; }
        public String getId() { return id; }
        @Override public String toString() { return titulo; }
    }

    private static class EstadoVisual {
        String texto;
        String styleClass; // Usado para enlazar con CSS
        public EstadoVisual(String texto, String styleClass) { this.texto = texto; this.styleClass = styleClass; }
    }
    
 // ==========================================================
    // SEGURIDAD VISUAL (RBAC)
    // ==========================================================
    public void configurarVistaSocio() {
        if (btnNuevoEjemplar != null) {
            btnNuevoEjemplar.setVisible(false);
            btnNuevoEjemplar.setManaged(false);
        }
    }

    @FXML
    public void initialize() {
    	
    	
        // Inicializar combos de registro
        cmbNuevoEstado.getItems().addAll("DISPONIBLE", "MANTENIMIENTO");
        cmbEnVenta.getItems().addAll("SÍ", "NO");
        
        // Inicializar Filtros y Ordenación
        cmbFiltroEstado.getItems().addAll("Todos", "Disponible", "Prestado", "Reservado", "Cuarentena", "Mantenimiento");
        cmbFiltroDureza.getItems().addAll("Todos", "Familiar (< 2.0)", "Medio (2.0 - 3.5)", "Experto (> 3.5)");
        cmbFiltroDuracion.getItems().addAll("Todos", "Corto (< 45)", "Medio (45 - 90)", "Largo (> 90)");
        cmbOrdenar.getItems().addAll("A-Z", "Puntuación: Mayor a Menor", "Año: Más reciente");
        
        limpiarFiltros(); // Pone todo en valores por defecto

        // Configurar Envoltorios de Colecciones en Memoria
        listaFiltrada = new FilteredList<>(listaMaestra, p -> true);
        listaOrdenada = new SortedList<>(listaFiltrada);

        // Listeners Reactivos a los Filtros
        txtBuscarEjemplar.textProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
        txtNumJugadores.textProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
        cmbFiltroEstado.valueProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
        cmbFiltroDureza.valueProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
        cmbFiltroDuracion.valueProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
        cmbOrdenar.valueProperty().addListener((obs, oldVal, newVal) -> actualizarOrdenacion());

        // Listener Buscador de Formulario
        txtBuscarRefLocal.textProperty().addListener((observable, oldValue, newValue) -> {
            debounceBusquedaRegistro.setOnFinished(event -> buscarReferenciaLocalTiempoReal(newValue.trim()));
            debounceBusquedaRegistro.playFromStart();
        });

        mostrarVisorDetalles();
        cargarEjemplaresDesdeBackend();
    }

    @FXML
    private void limpiarFiltros() {
        txtBuscarEjemplar.clear();
        txtNumJugadores.clear();
        cmbFiltroEstado.setValue("Todos");
        cmbFiltroDureza.setValue("Todos");
        cmbFiltroDuracion.setValue("Todos");
        cmbOrdenar.setValue("A-Z");
    }

    // ==========================================================
    // MAGIA DE FILTRADO Y ORDENACIÓN EN MEMORIA
    // ==========================================================
    private void actualizarFiltros() {
        listaFiltrada.setPredicate(ej -> {
            // 1. Filtro Texto
            String txt = txtBuscarEjemplar.getText().toLowerCase();
            boolean matchTexto = txt.isEmpty() || ej.titulo.toLowerCase().contains(txt) || ej.autor.toLowerCase().contains(txt);

            // 2. Filtro Estado (Cálculo dinámico Cuarentena)
            String estFiltro = cmbFiltroEstado.getValue();
            EstadoVisual ev = determinarEstadoVisual(ej);
            boolean matchEstado = "Todos".equals(estFiltro) || ev.texto.equalsIgnoreCase(estFiltro);

         // 3. Filtro Jugadores Recomendados (¡Inteligente!)
            boolean matchJug = true;
            String txtJug = txtNumJugadores.getText().trim();
            if (!txtJug.isEmpty()) {
                matchJug = esJugadorRecomendado(ej.jugadoresRecomendados, txtJug);
            }

            // 4. Filtro Dureza
            boolean matchDureza = true;
            String durFiltro = cmbFiltroDureza.getValue();
            if (!"Todos".equals(durFiltro)) {
                double d = ej.dureza.isEmpty() ? 0.0 : Double.parseDouble(ej.dureza);
                if (durFiltro.startsWith("Familiar")) matchDureza = (d > 0 && d < 2.0);
                else if (durFiltro.startsWith("Medio")) matchDureza = (d >= 2.0 && d <= 3.5);
                else if (durFiltro.startsWith("Experto")) matchDureza = (d > 3.5);
            }

            // 5. Filtro Duración
            boolean matchTiempo = true;
            String timeFiltro = cmbFiltroDuracion.getValue();
            if (!"Todos".equals(timeFiltro)) {
                int t = ej.duracionMinutos.isEmpty() ? 0 : Integer.parseInt(ej.duracionMinutos);
                if (timeFiltro.startsWith("Corto")) matchTiempo = (t > 0 && t < 45);
                else if (timeFiltro.startsWith("Medio")) matchTiempo = (t >= 45 && t <= 90);
                else if (timeFiltro.startsWith("Largo")) matchTiempo = (t > 90);
            }

            return matchTexto && matchEstado && matchJug && matchDureza && matchTiempo;
        });
        
        renderizarGrid();
    }
    /**
     * Comprueba si el número introducido está dentro de las recomendaciones de la BGG.
     * Soporta listas ("3, 4") y rangos ("3-5").
     */
    private boolean esJugadorRecomendado(String recomendados, String numFiltro) {
        if (recomendados == null || recomendados.isEmpty() || recomendados.equalsIgnoreCase("N/A")) {
            return false;
        }
        try {
            int num = Integer.parseInt(numFiltro);
            
            // 1. Busca coincidencias exactas aisladas (ej: el "4" en "3, 4, 5")
            if (recomendados.matches(".*\\b" + num + "\\b.*")) {
                return true;
            }
            
            // 2. Busca dentro de rangos (ej: si busca "4" y el texto pone "3-6")
            String[] fragmentos = recomendados.split("[^0-9\\-]+");
            for (String frag : fragmentos) {
                if (frag.contains("-")) {
                    String[] limites = frag.split("-");
                    if (limites.length == 2) {
                        int min = Integer.parseInt(limites[0]);
                        int max = Integer.parseInt(limites[1]);
                        if (num >= min && num <= max) return true;
                    }
                }
            }
        } catch (NumberFormatException e) { 
            // Si el usuario escribe letras en el filtro, simplemente no coincide
            return false; 
        }
        
        return false;
    }
    
    
    private void actualizarOrdenacion() {
        listaOrdenada.setComparator((ej1, ej2) -> {
            String orden = cmbOrdenar.getValue();
            if (orden == null) return 0;
            
            if (orden.startsWith("A-Z")) {
                return ej1.titulo.compareToIgnoreCase(ej2.titulo);
            } else if (orden.startsWith("Puntuación")) {
                double p1 = ej1.puntuacionBgg.isEmpty() ? 0.0 : Double.parseDouble(ej1.puntuacionBgg);
                double p2 = ej2.puntuacionBgg.isEmpty() ? 0.0 : Double.parseDouble(ej2.puntuacionBgg);
                return Double.compare(p2, p1); // Invertido para Mayor a Menor
            } else if (orden.startsWith("Año")) {
                int a1 = ej1.ano.isEmpty() ? 0 : Integer.parseInt(ej1.ano);
                int a2 = ej2.ano.isEmpty() ? 0 : Integer.parseInt(ej2.ano);
                return Integer.compare(a2, a1); // Invertido para Más reciente
            }
            return 0;
        });
        renderizarGrid();
    }

    private void renderizarGrid() {
        gridEjemplares.getChildren().clear();
        for (EjemplarDTO ej : listaOrdenada) {
            gridEjemplares.getChildren().add(crearTarjetaEjemplar(ej));
        }
    }

    // ==========================================================
    // MAGIA DE ESTADOS (ESTILOS POR CLASES CSS)
    // ==========================================================
    private EstadoVisual determinarEstadoVisual(EjemplarDTO ej) {
        String estadoDb = ej.estado.toUpperCase();
        if ("DISPONIBLE".equals(estadoDb)) {
            try {
                LocalDate fechaAdq = LocalDate.parse(ej.fechaAdquisicion);
                if (ChronoUnit.DAYS.between(fechaAdq, LocalDate.now()) <= 30) {
                    return new EstadoVisual("CUARENTENA", "badge-cuarentena");
                }
            } catch (Exception e) { }
        }
        switch (estadoDb) {
            case "DISPONIBLE": return new EstadoVisual("DISPONIBLE", "badge-disponible");
            case "RESERVADO":  return new EstadoVisual("RESERVADO", "badge-reservado");
            case "PRESTADO":   return new EstadoVisual("PRESTADO", "badge-prestado");
            case "MANTENIMIENTO": return new EstadoVisual("MANTENIMIENTO", "badge-mantenimiento");
            default:           return new EstadoVisual(estadoDb, "badge-default");
        }
    }

    // ==========================================================
    // BACKEND
    // ==========================================================
    private void cargarEjemplaresDesdeBackend() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8081/api/ejemplares")).GET().build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        List<EjemplarDTO> ejemplares = parsearEjemplaresAnidados(response.body());
                        Platform.runLater(() -> {
                            listaMaestra.setAll(ejemplares);
                            actualizarFiltros();
                            actualizarOrdenacion();
                        });
                    }
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    // (El resto de métodos: buscarReferenciaLocal, crearTarjeta, guardar, parsear son idénticos a tu versión actual)
    
    private void buscarReferenciaLocalTiempoReal(String query) {
        cmbJuegoReferencia.getItems().clear();
        if (query.isEmpty()) { cmbJuegoReferencia.hide(); return; }
        String url = "http://localhost:8081/api/juegos/buscar?query=" + query.replace(" ", "%20");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
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
        card.getStyleClass().add("card-ejemplar");
        card.setPrefWidth(140);
        card.setPrefHeight(200);

        ImageView imgView = new ImageView();
        imgView.setFitWidth(120); imgView.setFitHeight(150); imgView.setPreserveRatio(true);
        if (!ej.urlImagen.isEmpty()) imgView.setImage(new Image(ej.urlImagen, true));

        Label lblTitulo = new Label(ej.titulo);
        lblTitulo.setWrapText(true); lblTitulo.setAlignment(Pos.CENTER); lblTitulo.setMaxWidth(Double.MAX_VALUE);
        
        EstadoVisual estadoVisual = determinarEstadoVisual(ej);
        lblTitulo.getStyleClass().addAll("badge-estado", estadoVisual.styleClass);

        card.getChildren().addAll(imgView, lblTitulo);
        card.setOnMouseClicked(event -> { volcarDatosAlVisor(ej, estadoVisual); mostrarVisorDetalles(); });
        return card;
    }

    private void volcarDatosAlVisor(EjemplarDTO ej, EstadoVisual estadoVisual) {
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

        lblDetalleCodigo.setText(ej.codigoLocal);
        lblDetalleBalda.setText(ej.balda);
        lblDetalleAdquisicion.setText(ej.fechaAdquisicion);
        lblDetalleEnVenta.setText(ej.enVenta ? "SÍ" : "NO");
        txtDetalleAnotaciones.setText(ej.anotaciones);
        
        lblDetalleEstado.setText(estadoVisual.texto);
        // Limpiar clases previas para no mezclar colores
        lblDetalleEstado.getStyleClass().removeAll("badge-disponible", "badge-reservado", "badge-prestado", "badge-cuarentena", "badge-mantenimiento");
        lblDetalleEstado.getStyleClass().add(estadoVisual.styleClass);
        
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
        cmbNuevoEstado.setValue("DISPONIBLE"); cmbEnVenta.setValue("NO");
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

        String jsonBody = String.format("{\"codigoLocal\":\"%s\", \"balda\":\"%s\", \"fechaAdquisicion\":\"%s\", \"estado\":\"%s\", \"enVenta\":%b, \"anotaciones\":\"%s\", \"juegoReferencia\":{\"id\":%s}}",
            codigo, balda, fecha.toString(), estado, enVenta, notas, juegoSel.getId());

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8081/api/ejemplares")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
                    Platform.runLater(() -> {
                        if (response.statusCode() == 200 || response.statusCode() == 201) {
                            mostrarAlerta("Éxito", "Ejemplar añadido.", Alert.AlertType.INFORMATION);
                            cancelarRegistro(); cargarEjemplaresDesdeBackend(); // Recargar DB
                        } else { mostrarAlerta("Error", "No se guardó: " + response.body(), Alert.AlertType.ERROR); }
                    });
                });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void limpiarFormulario() {
        txtBuscarRefLocal.clear(); cmbJuegoReferencia.getItems().clear();
        txtNuevoCodigo.clear(); txtNuevaBalda.clear(); dpFechaAdquisicion.setValue(null); txtNuevasAnotaciones.clear();
    }

    // Funciones de Parseo intactas
    private List<String> extraerObjetosDelArray(String jsonArray) {
        List<String> objetos = new ArrayList<>();
        int nivel = 0, inicio = -1;
        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);
            if (c == '{') { if (nivel == 0) inicio = i; nivel++; } else if (c == '}') { nivel--; if (nivel == 0 && inicio != -1) { objetos.add(jsonArray.substring(inicio, i + 1)); } }
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
            ej.estado = extraerStringJson(frag, "estado"); if(ej.estado.isEmpty()) ej.estado = "DISPONIBLE";
            ej.balda = extraerStringJson(frag, "balda"); ej.fechaAdquisicion = extraerStringJson(frag, "fechaAdquisicion");
            ej.anotaciones = extraerStringJson(frag, "anotaciones"); ej.enVenta = frag.contains("\"enVenta\":true");
            int idxUser = frag.indexOf("\"prestadoA\""); if (idxUser != -1 && !frag.substring(idxUser).startsWith("\"prestadoA\":null")) ej.prestadoA = extraerStringJson(frag.substring(idxUser), "username"); 
            int idxRef = frag.indexOf("\"juegoReferencia\"");
            if (idxRef != -1) {
                String subJson = frag.substring(idxRef);
                ej.idJuego = extraerNumeroJson(subJson, "id"); ej.titulo = extraerStringJson(subJson, "titulo"); ej.urlImagen = extraerStringJson(subJson, "urlImagen");
                ej.ano = extraerNumeroJson(subJson, "anoPublicacion"); ej.descripcion = extraerStringJson(subJson, "descripcion");
                ej.minJugadores = extraerNumeroJson(subJson, "minJugadores"); ej.maxJugadores = extraerNumeroJson(subJson, "maxJugadores");
                ej.duracionMinutos = extraerNumeroJson(subJson, "duracionMinutos"); ej.dureza = extraerNumeroJson(subJson, "dureza"); 
                ej.autor = extraerStringJson(subJson, "autor"); ej.puntuacionBgg = extraerNumeroJson(subJson, "puntuacionBgg");
                ej.jugadoresRecomendados = extraerStringJson(subJson, "jugadoresRecomendados");
                ej.categoria = extraerStringJson(subJson, "categoria"); ej.mecanicas = extraerStringJson(subJson, "mecanicas");
            }
            lista.add(ej);
        }
        return lista;
    }

    private List<JuegoRefCombo> parsearJuegosCombo(String jsonBody) {
        List<JuegoRefCombo> lista = new ArrayList<>();
        List<String> fragmentos = extraerObjetosDelArray(jsonBody);
        for (String frag : fragmentos) { lista.add(new JuegoRefCombo(extraerNumeroJson(frag, "id"), extraerStringJson(frag, "titulo"))); }
        return lista;
    }
    private String extraerStringJson(String json, String clave) { String patron = "\"" + clave + "\":\""; int inicio = json.indexOf(patron); if (inicio == -1) return ""; inicio += patron.length(); int fin = json.indexOf("\"", inicio); return fin == -1 ? "" : json.substring(inicio, fin); }
    private String extraerNumeroJson(String json, String clave) { String patron = "\"" + clave + "\":"; int inicio = json.indexOf(patron); if (inicio == -1) return ""; inicio += patron.length(); int fin = json.indexOf(",", inicio); int finBrace = json.indexOf("}", inicio); if (fin == -1 || (finBrace != -1 && finBrace < fin)) fin = finBrace; return fin == -1 ? "" : json.substring(inicio, fin).trim(); }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) { Alert alert = new Alert(tipo); alert.setTitle(titulo); alert.setHeaderText(null); alert.setContentText(mensaje); alert.showAndWait(); }
}