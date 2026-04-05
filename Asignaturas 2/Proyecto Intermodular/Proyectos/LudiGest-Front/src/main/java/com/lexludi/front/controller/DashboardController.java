package com.lexludi.front.controller;

import com.lexludi.front.model.Usuario; // Asegurate de tener este paquete y clase creados

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Controlador principal del Dashboard.
 * Gestiona la navegacion y la seguridad visual (RBAC).
 */
public class DashboardController {

    @FXML private Button btnCatalogo;
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
}