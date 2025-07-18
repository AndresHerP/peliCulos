package org.example.peliculascolab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class eliminar {
    // Componentes FXML
    @FXML
    private MenuButton menuButton;
    @FXML
    private Label nombrePeli;
    @FXML
    private Button eliminarButton;

    // Datos de películas
    private ObservableList<Consulta.Movie> movieData = FXCollections.observableArrayList();
    private String selectedMovie;

    @FXML
    public void initialize() {
        loadDataFromDatabase();
        eliminarButton.setOnAction(event -> eliminarPelicula());
    }

    private void loadDataFromDatabase() {
        String wallet = "G:\\1-UTEZ\\3-Cuatrimestre\\POO\\Trabajos\\Semana12\\src\\Wallet"; // CAMBIAR LA DIRECCIÓN EN LA ENTREGA FINAL O CLONANDO
        System.setProperty("oracle.net.tns_admin", wallet);
        String jdbcurl = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
        String userName = "ADMIN";
        String password = "Biblioteca01";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(jdbcurl, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM peliculas");

            while (rs.next()) {
                String movieTitle = rs.getString(2);
                MenuItem menuItem = new MenuItem(movieTitle);
                menuItem.setOnAction(event -> seleccionarPelicula(movieTitle));
                menuButton.getItems().add(menuItem);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al cargar datos:");
            e.printStackTrace();
        }
    }

    private void seleccionarPelicula(String movieTitle) {
        selectedMovie = movieTitle;
        nombrePeli.setText("Se eliminara la pelicula: " + selectedMovie);
    }

    private void eliminarPelicula() {
        if (selectedMovie != null) {
            String wallet = "/home/alex/Documentos/Utez/3ro/Poo/BaseDatos/src/Wallet"; // CAMBIAR LA DIRECCIÓN EN LA ENTREGA FINAL O CLONANDO
            System.setProperty("oracle.net.tns_admin", wallet);
            String jdbcurl = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
            String userName = "ADMIN";
            String password = "Biblioteca01";

            try {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection con = DriverManager.getConnection(jdbcurl, userName, password);
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM peliculas WHERE titulo = '" + selectedMovie + "'");
                stmt.close();
                con.close();

                nombrePeli.setText("Película eliminada: " + selectedMovie);
                selectedMovie = null;
                menuButton.getItems().clear();
                loadDataFromDatabase();
            } catch (Exception e) {
                System.out.println("Error al eliminar la película:");
                e.printStackTrace();
            }
        } else {
            nombrePeli.setText("No se ha seleccionado ninguna película.");
        }
    }
}
