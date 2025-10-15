module TareaUD1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics; // <--- asegura que cualquier cliente vea Stage
    exports application; // IMPORTANTE: ahora exportamos el paquete 'application'
}

