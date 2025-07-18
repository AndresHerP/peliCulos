package org.example.peliculascolab;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class EditarController {

    @FXML
    private ComboBox<String> peliculaComboBox;

    @FXML
    private TextField nuevoTituloField;

    @FXML
    private TextField nuevoGeneroField;

    @FXML
    private TextField nuevoAnioField;

    @FXML
    private Label statusLabel;

    private String selectedMovie;

    @FXML
    public void initialize() {
        cargarPeliculas();
        peliculaComboBox.setOnAction(e -> selectedMovie = peliculaComboBox.getValue());
    }

    private void cargarPeliculas() {
        try {
            Connection con = getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT titulo FROM peliculas");

            while (rs.next()) {
                peliculaComboBox.getItems().add(rs.getString("titulo"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            statusLabel.setText("Error cargando películas.");
            e.printStackTrace();
        }
    }

    @FXML
    public void modificarPelicula() {
        if (selectedMovie == null) {
            statusLabel.setText("Selecciona una película.");
            return;
        }

        String nuevoTitulo = nuevoTituloField.getText();
        String nuevoGenero = nuevoGeneroField.getText();
        String nuevoAnio = nuevoAnioField.getText();

        if (nuevoTitulo.isEmpty() || nuevoGenero.isEmpty() || nuevoAnio.isEmpty()) {
            statusLabel.setText("Todos los campos son requeridos.");
            return;
        }

        try {
            Connection con = getConexion();
            PreparedStatement pstmt = con.prepareStatement(
                    "UPDATE peliculas SET titulo = ?, genero = ?, año = ? WHERE titulo = ?");
            pstmt.setString(1, nuevoTitulo);
            pstmt.setString(2, nuevoGenero);
            pstmt.setInt(3, Integer.parseInt(nuevoAnio));
            pstmt.setString(4, selectedMovie);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                statusLabel.setText("Película actualizada correctamente.");
                peliculaComboBox.getItems().clear();
                cargarPeliculas();
            } else {
                statusLabel.setText("No se pudo actualizar.");
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            statusLabel.setText("Error modificando película.");
            e.printStackTrace();
        }
    }

    private Connection getConexion() throws Exception {
        String wallet = "C:\\Users\\Kishimuz\\Desktop\\prueba\\peliCulos\\src\\Wallet";
        System.setProperty("oracle.net.tns_admin", wallet);
        String jdbcurl = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
        String userName = "ADMIN";
        String password = "Biblioteca01";
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(jdbcurl, userName, password);
    }
    @FXML
    protected void volverMenu() {
        Stage stage = (Stage) nuevoTituloField.getScene().getWindow();
        stage.close();
    }
}
