package com.lexludi.front.controller;

import java.io.IOException;
// --- FIN PARTE NUEVA ---

import com.lexludi.front.model.Usuario; // Asegurate de tener este paquete y clase creados

import javafx.fxml.FXML;
// --- INICIO PARTE NUEVA: Importaciones para inyección de vistas ---
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Controlador principal del Dashboard.
 * Gestiona la navegacion y la seguridad visual (RBAC).
 */
public class DashboardController {

    @FXML private Button btnCatalogo;
    @FXML private Button btnJuegosReferencia;
    @FXML private Button btnSocios;
    @FXML private Button btnEventos;
    @FXML private Button btnPrestamos;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnMisPrestamos;
    @FXML private Button btnMisReservas;
    @FXML private Button btnSalir;
    
    @FXML private StackPane contentArea;

    private Usuario usuarioLogueado;

    /**
     * Inicializa los datos del controlador tras cargar la vista.
     * Aplica la logica de roles para mostrar/ocultar botones.
     * @param usuario El usuario autenticado proveniente del Login.
     */
    public void initData(Usuario usuario) {
        this.usuarioLogueado = usuario;
        
        String rol = usuario.getRol();

        if ("SOCIO".equalsIgnoreCase(rol)) {
            // Un socio no puede gestionar otros socios ni ver configuracion global
            ocultarBoton(btnSocios);
            ocultarBoton(btnConfiguracion);
            ocultarBoton(btnJuegosReferencia);
            // Si puede ver sus propios prestamos y reservas
            mostrarBoton(btnMisPrestamos);
            mostrarBoton(btnMisReservas);
            
        } else if ("ADMIN".equalsIgnoreCase(rol)) {
            // Un admin gestiona de forma global, no tiene sentido ver "sus" reservas como socio
            mostrarBoton(btnSocios);
            mostrarBoton(btnConfiguracion);
            
            ocultarBoton(btnMisPrestamos);
            ocultarBoton(btnMisReservas);
        }
    }

    private void ocultarBoton(Button boton) {
        boton.setVisible(false);
        boton.setManaged(false); // Vital para que no ocupe espacio vacio en el VBox
    }

    private void mostrarBoton(Button boton) {
        boton.setVisible(true);
        boton.setManaged(true);
    }

    // --- INICIO PARTE NUEVA: Método para inyectar la vista de usuarios ---
    /**
     * Método para cargar la vista de gestión de usuarios.
     * Se debe llamar desde el botón "Socios/Usuarios" (btnSocios) del menú lateral en tu dashboard.fxml
     */
    @FXML
    public void abrirModuloUsuarios() {
        try {
            // Cargamos el archivo FXML del módulo de usuarios
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usuarios.fxml"));
            Parent view = loader.load();
            
            // Limpiamos el área de contenido actual y añadimos la nueva vista inyectada
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            e.printStackTrace();
            // Mostramos un alert si falla la carga del archivo FXML
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Carga");
            alert.setHeaderText("No se pudo cargar el módulo de usuarios");
            alert.setContentText("Detalle: " + e.getMessage());
            alert.showAndWait();
        }
    }
    /**
     * Método para cargar la vista de Base de Datos de Juegos (Referencia).
     * Se llama desde el botón "Base de Datos Juegos" del menú lateral.
     */
    @FXML
    public void abrirModuloJuegosReferencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/juegos_referencia.fxml"));
            Parent view = loader.load();
            
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Carga");
            alert.setHeaderText("No se pudo cargar el módulo de Juegos de Referencia");
            alert.setContentText("Detalle: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
 // === INICIO PARTE NUEVA: Método para inyectar la vista del Catálogo ===
    /**
     * Método para cargar la vista del Catálogo Físico.
     * Se llama desde el botón "Catálogo" del menú lateral.
     */
    @FXML
    public void abrirModuloCatalogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/catalogo.fxml"));
            Parent view = loader.load();
            
            // ¡LA MAGIA!: Obtenemos el controlador de la vista que acabamos de cargar
            CatalogoController catalogoController = loader.getController();
            
            // Si el usuario es un SOCIO, le ordenamos al catálogo que aplique sus restricciones
            if (usuarioLogueado != null && "SOCIO".equalsIgnoreCase(usuarioLogueado.getRol())) {
                catalogoController.configurarVistaSocio();
            }
            
            // Inyectamos la vista en el área principal
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Carga");
            alert.setHeaderText("No se pudo cargar el módulo de Catálogo");
            alert.setContentText("Detalle: " + e.getMessage());
            alert.showAndWait();
        }
    }
    // === FIN PARTE NUEVA ===
}