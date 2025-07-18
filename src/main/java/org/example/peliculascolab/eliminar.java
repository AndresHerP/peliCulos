package org.example.peliculascolab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class eliminar {
    private static final String JDBC_URL = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
    private static final String USERNAME = "ADMIN";
    private static final String PASSWORD = "Biblioteca01";

    @FXML private TextField idField;
    @FXML private Label tituloLabel;
    @FXML private Label generoLabel;
    @FXML private Label anioLabel;
    @FXML private Label mensajeLabel;

    static {
        String ubicacionWallet = "src/Wallet/";
        System.setProperty("oracle.net.tns_admin", ubicacionWallet);
    }

    @FXML
    private void buscarPelicula() {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            mensajeLabel.setText("Por favor ingrese un ID de película");
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT titulo, genero, año FROM peliculas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tituloLabel.setText(rs.getString("titulo"));
                generoLabel.setText(rs.getString("genero"));
                anioLabel.setText(String.valueOf(rs.getInt("año")));
                mensajeLabel.setText("");
            } else {
                mensajeLabel.setText("No se encontró una película con ese ID");
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            mensajeLabel.setText("El ID debe ser un número");
            limpiarCampos();
        } catch (SQLException e) {
            mensajeLabel.setText("Error al conectar con la base de datos: " + e.getMessage());
            limpiarCampos();
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarPelicula() {
        String id = idField.getText().trim();

        if (id.isEmpty() || tituloLabel.getText().isEmpty()) {
            mensajeLabel.setText("Busque una película primero");
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM peliculas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mensajeLabel.setText("Película eliminada exitosamente");
                limpiarCampos();
                idField.setText("");
            } else {
                mensajeLabel.setText("No se pudo eliminar la película");
            }
        } catch (SQLException e) {
            mensajeLabel.setText("Error al eliminar la película: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        tituloLabel.setText("");
        generoLabel.setText("");
        anioLabel.setText("");
    }

    @FXML
    protected void volverMenu() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}