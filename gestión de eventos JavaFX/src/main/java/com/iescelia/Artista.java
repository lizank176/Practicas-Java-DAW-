package com.iescelia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Artista extends Persona {
    private String fotografia;
    private String obra_destacada;
    public static ObservableList<Artista> listaArtistas = FXCollections.observableArrayList();
    public Artista(int id, String nombre, String apellido1, String apellido2, String fotografia, String obra_destacada) {
        super(id, nombre, apellido1, apellido2);
        this.fotografia = fotografia;
        this.obra_destacada = obra_destacada;
    }
    public String getFotografia() {
        return fotografia;
    }
    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }
    public String getObra_destacada() {
        return obra_destacada;
    }
    public void setObra_destacada(String obra_destacada) {
        this.obra_destacada = obra_destacada;
    }
    public String toString() {
        return fotografia + " - " + obra_destacada;
    }
    @Override
    public Persona getById(int id) {
       Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM artista");
            while (rs.next()) {
                if(rs.getInt("id") == id) {
                    Artista artista = new Artista(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("fotografia"), rs.getString("obra_destacada"));
                    return artista;
                }
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }  
        return null;
    }
    /**
     * Método para obtener todos los artistas de la base de datos y guardarlos en la lista de artistas.
     */
    public static void getAll() {
    String query = "SELECT p.id, p.nombre, p.apellido1, p.apellido2, a.fotografia, a.obra_destacada " +
                   "FROM persona p INNER JOIN artista a ON p.id = a.id_persona";

    listaPersonas.clear();
    listaArtistas.clear();

    try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Artista artista = new Artista(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("fotografia"),
                rs.getString("obra_destacada")
            );

            listaPersonas.add(artista);
            listaArtistas.add(artista);
        }

    } catch (Exception e) {
        System.out.println("Error de SQL: " + e.getMessage());
    }
}

    public int save() {
        String sqlSelect = "SELECT 1 FROM persona WHERE id = ?";
        String sqlUpdatePersona = "UPDATE persona SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE id = ?";
        String sqlUpdateArtista = "UPDATE artista SET fotografia = ?, obra_destacada = ? WHERE id_persona = ?";
        String sqlInsertPersona = "INSERT INTO persona (id, nombre, apellido1, apellido2, tipo) VALUES (?, ?, ?, ?, 'Artista')";
        String sqlInsertArtista = "INSERT INTO artista (id_persona, fotografia, obra_destacada) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root")) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            boolean exists;
            try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, getId());
                try (ResultSet rs = psSelect.executeQuery()) {
                    exists = rs.next();
                }
            }

            if (exists) {
                try (PreparedStatement psUpdatePersona = con.prepareStatement(sqlUpdatePersona);
                    PreparedStatement psUpdateArtista = con.prepareStatement(sqlUpdateArtista)) {

                    psUpdatePersona.setString(1, getNombre());
                    psUpdatePersona.setString(2, getApellido1());
                    psUpdatePersona.setString(3, getApellido2());
                    psUpdatePersona.setInt(4, getId());
                    psUpdatePersona.executeUpdate();

                    psUpdateArtista.setString(1, getFotografia());
                    psUpdateArtista.setString(2, getObra_destacada());
                    psUpdateArtista.setInt(3, getId());
                    psUpdateArtista.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsertPersona = con.prepareStatement(sqlInsertPersona);
                    PreparedStatement psInsertArtista = con.prepareStatement(sqlInsertArtista)) {

                    psInsertPersona.setInt(1, getId());
                    psInsertPersona.setString(2, getNombre());
                    psInsertPersona.setString(3, getApellido1());
                    psInsertPersona.setString(4, getApellido2());
                    psInsertPersona.executeUpdate();

                    psInsertArtista.setInt(1, getId());
                    psInsertArtista.setString(2, getFotografia());
                    psInsertArtista.setString(3, getObra_destacada());
                    psInsertArtista.executeUpdate();
                }
            }

            return 1;
        } catch (Exception e) {
            System.err.println("Error de SQL: " + e.getMessage());
            return 0;
        }
    }
    @Override
    public int delete() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            int rowsAffected = st.executeUpdate("DELETE FROM artista WHERE id = " + getId());
            con.close();
            return rowsAffected > 0 ? 1 : 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
    @Override
    public void participa(ObservableList<Evento> nuevosEventos) {
    Connection con = null;
    try {
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
        con.setAutoCommit(false);  // Iniciar transacción

        // 1. Eliminar todas las participaciones existentes
        String deleteQuery = "DELETE FROM persona_evento  WHERE id_persona = ?";
        try (PreparedStatement ps = con.prepareStatement(deleteQuery)) {
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }

        // 2. Insertar las nuevas participaciones
        if (!nuevosEventos.isEmpty()) {
            String insertQuery = "INSERT INTO persona_evento (id_persona, id_evento, fecha) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                for (Evento evento : nuevosEventos) {
                    ps.setInt(1, this.getId());
                    ps.setInt(2, evento.getId());
                    ps.setString(3, LocalDate.now().toString()); // Fecha actual
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }

        con.commit();  // Confirmar transacción
        
        // 3. Actualizar la lista local
        this.listaEventosApuntados.setAll(nuevosEventos);

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();  // Revertir en caso de error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error al actualizar eventos: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void getByNombre(String txt) {
        listaArtistas.clear();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            String sql = "SELECT p.id, p.nombre, p.apellido1, p.apellido2, a.fotografia, a.obra_destacada " +
                         "FROM persona p INNER JOIN artista a ON p.id = a.id_persona " +
                         "WHERE p.nombre LIKE ? OR p.apellido1 LIKE ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + txt + "%");
            pstmt.setString(2, "%" + txt + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Artista artista = new Artista(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("fotografia"),
                        rs.getString("obra_destacada")
                );
                listaArtistas.add(artista);
            }
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
       
    }
    
    
}

