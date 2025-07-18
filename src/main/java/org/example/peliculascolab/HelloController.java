package org.example.peliculascolab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onAgregar() {
        abrirVentana("agregar.fxml", "Nueva pelicula");
    }

    @FXML
    protected void onConsultar() {
        abrirVentana("consulta.fxml", "Catalogo");
    }

    @FXML
    protected void onModificar() {
        abrirVentana("editar.fxml", "Modificar");
    }

    @FXML
    protected void onEliminar() {
        abrirVentana("eliminar.fxml", "Eliminar");
    }

    private void abrirVentana(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}