package org.example.peliculascolab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.stage.Stage;


public class Agregar {

    @FXML
    private TextField lbID;
    @FXML
    private TextField lbTitulo;
    @FXML
    private TextField lbGenero;
    @FXML
    private TextField idAno;
    @FXML
    private Label texExito;

    @FXML
    public void btnguardar() {
        String id = lbID.getText();
        String titulo = lbTitulo.getText();
        String genero = lbGenero.getText();
        String ano = idAno.getText();

        String ubicacionWallet = "C:\\Users\\Kishimuz\\Desktop\\prueba\\peliCulos\\src\\Wallet";
        System.setProperty("oracle.net.tns_admin", ubicacionWallet);
        String jdbcurl = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
        String userName = "ADMIN";
        String password = "Biblioteca01";

        String sql = "INSERT INTO peliculas (id, titulo, genero, año) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(jdbcurl, userName, password);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, titulo);
            stmt.setString(3, genero);
            stmt.setInt(4, Integer.parseInt(ano));
            stmt.executeUpdate();

            texExito.setText("Registro exitoso.");
            stmt.close();
            con.close();
        } catch (Exception e) {
            texExito.setText("Error al registrar.");
            e.printStackTrace();
        }

    }

    @FXML
    protected void volverMenu() {
        Stage stage = (Stage) lbTitulo.getScene().getWindow();
        stage.close();
    }
}
