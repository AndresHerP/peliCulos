package org.example.peliculascolab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Consulta {
    @FXML
    private TableView<Movie> movieTable;

    private ObservableList<Movie> movieData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadDataFromDatabase();
        movieTable.setItems(movieData);
    }

    private void loadDataFromDatabase() {
        String ubicacionWallet = "C:\\Users\\Kishimuz\\Desktop\\trabajo_collab\\PeliculasEquipo\\src\\Wallet";
        System.setProperty("oracle.net.tns_admin", ubicacionWallet);
        String jdbcurl = "jdbc:oracle:thin:@icl8aqfau8e0bzlc_high";
        String userName = "ADMIN";
        String password = "Biblioteca01";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(jdbcurl, userName, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM peliculas");

            while (rs.next()) {
                movieData.add(new Movie(
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

    // Clase modelo interna
    public static class Movie {
        private final int id;
        private final String titulo;
        private final String genero;
        private final int anio;

        public Movie(int id, String titulo, String genero, int anio) {
            this.id = id;
            this.titulo = titulo;
            this.genero = genero;
            this.anio = anio;
        }

        public int getId() { return id; }
        public String getTitulo() { return titulo; }
        public String getGenero() { return genero; }
        public int getAnio() { return anio; }
    }
}