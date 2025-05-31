package com.iescelia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public abstract class Persona {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    public static ObservableList<Persona> listaPersonas = FXCollections.observableArrayList();
    public  ObservableList<Evento> listaEventosApuntados = FXCollections.observableArrayList();
    public Persona(int id, String nombre, String apellido1, String apellido2) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String toString() {
        return id + " - " + nombre + " - " + apellido1 + " - " + apellido2;
    }
    //Métodos abstractos
    public abstract Persona getById(int id);
    public abstract  int save();
    public abstract int delete();
    public abstract void participa(ObservableList<Evento> nuevosEventos);
  
 
    public  ObservableList<Evento> getEventos() {
        ObservableList<Evento> eventosApuntados = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(  "SELECT evento.id, evento.nombre, evento.descripcion, evento.lugar, evento.fecha_inicio, evento.fecha_fin, evento.id_categoria FROM persona INNER JOIN persona_evento ON persona.id = persona_evento.id_persona INNER JOIN evento ON persona_evento.id_evento = evento.id WHERE id_persona = "+this.id); 
            while (rs.next()) {
                int idEvento = rs.getInt("id");
                String nombreEvento = rs.getString("nombre");
                String fechaEvento = rs.getString("fecha_inicio");
                String horaEvento = rs.getString("fecha_fin");
                String lugarEvento = rs.getString("lugar");
                String descripcionEvento = rs.getString("descripcion");
                int idCategoria = rs.getInt("id_categoria");
                Evento evento = new Evento(idEvento, nombreEvento, lugarEvento, fechaEvento , horaEvento, descripcionEvento, idCategoria);
                listaEventosApuntados.add(evento);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de SQL al obtener los eventos de la persona: " + e.getMessage());
        }   
        return listaEventosApuntados;
    }
    public static int getLastId() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) AS maximo FROM persona");
            while (rs.next()) {
                return rs.getInt("maximo");
            }
            con.close();
            return 1;
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return 0;
    }
    

}
