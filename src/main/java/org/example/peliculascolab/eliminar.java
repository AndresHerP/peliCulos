package org.example.peliculascolab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class eliminar {
    // VARIABLES LOCALES
    @FXML
    private TableView<Consulta.Movie> movieTable;

    private ObservableList<Consulta.Movie> movieData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadDataFromDatabase();
        movieTable.setItems(movieData);
    }
    // FIN DE VARIABLES LOCALES

    // CARGA DE BASE DE DATOS
    private void loadDataFromDatabase() {
        String wallet = "G:\\1-UTEZ\\3-Cuatrimestre\\POO\\Trabajos\\Semana12\\src\\Wallet";
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
                movieData.add(new Consulta.Movie(
                        rs.getInt(1), // ID
                        rs.getString(2), // Título
                        rs.getString(3), // Autor
                        rs.getInt(4)  // Año
                ));
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al cargar datos:");
            e.printStackTrace();
        }
    }
    // FIN DE LA CARGA DE BASE DE DATOS

    // METODO PARA ELIMINAR
    public static class eliminarPelicula{
        // 

    }

}
