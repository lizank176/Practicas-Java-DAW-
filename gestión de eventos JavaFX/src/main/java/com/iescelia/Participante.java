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

public class Participante extends Persona {
    private String email;
    public static ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();
    public Participante(int id, String nombre, String apellido1, String apellido2, String email) {
        super(id, nombre, apellido1, apellido2);
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String toString() {
        return super.toString() + " - " + email;
    }
    @Override
    public Persona getById(int id) {
      Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM participante");
            while (rs.next()) {
                if(rs.getInt("id") == id) {
                    Participante participante = new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"),  rs.getString("email"));
                    return participante;
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
    String query = "SELECT p.id, p.nombre, p.apellido1, p.apellido2,  part.email " +
                   "FROM persona p INNER JOIN participante part ON p.id = part.id_persona";

    listaPersonas.clear();
    listaParticipantes.clear();

    try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Participante participante = new Participante(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("email")
            );

            listaPersonas.add(participante);
            listaParticipantes.add(participante);
        }

    } catch (Exception e) {
        System.out.println("Error de SQL: " + e.getMessage());
    }
}
    @Override
    public int save() {
        String sqlSelect = "SELECT 1 FROM persona WHERE id = ?";
        String sqlUpdatePersona = "UPDATE persona SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE id = ?";
        String sqlUpdateParticipante = "UPDATE participante SET email = ? WHERE id_persona = ?";
        String sqlInsertPersona = "INSERT INTO persona (id, nombre, apellido1, apellido2, tipo) VALUES (?, ?, ?, ?, 'Participante')";
        String sqlInsertParticipante = "INSERT INTO participante (id_persona, email) VALUES (?, ?, ?)";

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
                    PreparedStatement psUpdateParticipante = con.prepareStatement(sqlUpdateParticipante)) {

                    psUpdatePersona.setString(1, getNombre());
                    psUpdatePersona.setString(2, getApellido1());
                    psUpdatePersona.setString(3, getApellido2());
                    psUpdatePersona.setInt(4, getId());
                    psUpdatePersona.executeUpdate();

                    psUpdateParticipante.setString(1, getEmail());
                    psUpdateParticipante.setInt(2, getId());
                    psUpdateParticipante.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsertPersona = con.prepareStatement(sqlInsertPersona);
                    PreparedStatement InsertParticipante = con.prepareStatement(sqlInsertParticipante)) {

                    psInsertPersona.setInt(1, getId());
                    psInsertPersona.setString(2, getNombre());
                    psInsertPersona.setString(3, getApellido1());
                    psInsertPersona.setString(4, getApellido2());
                    psInsertPersona.executeUpdate();

                    InsertParticipante.setInt(1, getId());
                    InsertParticipante.setString(2, getEmail());
                    InsertParticipante.executeUpdate();
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
            int rowsAffected = st.executeUpdate("DELETE FROM participante WHERE id = " + getId());
            con.close();
            return rowsAffected > 0 ? 1 : 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
        
    }
    
    
    public static void getByNombre(String txt) {
        listaParticipantes.clear();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            String sql = "SELECT p.id, p.nombre, p.apellido1, p.apellido2, part.email " +
                         "FROM persona p INNER JOIN participante part ON p.id = part.id_persona " +
                         "WHERE p.nombre LIKE ? OR p.apellido1 LIKE ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + txt + "%");
            pstmt.setString(2, "%" + txt + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Participante participante = new Participante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("email")
                );
                listaParticipantes.add(participante);
            }
        } catch (Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
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
    
    
}
