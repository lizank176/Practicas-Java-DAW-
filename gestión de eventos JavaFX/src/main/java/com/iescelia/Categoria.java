package com.iescelia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
   
    public static ObservableList<Categoria> listaCategoria = FXCollections.observableArrayList();
    public Categoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
     
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
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String toString() {
        return id + " - " + nombre + " - " + descripcion ;
    }

  
    public static  void  getAll() {
        Connection con = null;
        listaCategoria.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categoria");
            while (rs.next()) {
                Categoria u = new Categoria(rs.getInt("id"), rs.getString("nombre"),  rs.getString("descripcion"));
                listaCategoria.add(u);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }    
    }
  
    public  static Categoria getByIdCategoria(int id) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categoria");
            while (rs.next()) {
                if(rs.getInt("id") == id) {
                    Categoria u = new Categoria(rs.getInt("id"), rs.getString("nombre"),  rs.getString("descripcion"));
                    return  u;
                }
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
            
        } 
        return null; 
       
    }

    public static void getByNombre(String txt) {
        Connection con = null;
        listaCategoria.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categoria WHERE nombre LIKE '%" + txt + "%' OR descripcion LIKE '%" + txt + "%'");
            while (rs.next()) {
                Categoria u = new Categoria(rs.getInt("id"), rs.getString("nombre"),  rs.getString("descripcion"));

                listaCategoria.add(u);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }  
    }

    public static int getLastId() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM categoria");
            while (rs.next()) {
                return rs.getInt("id");
            }
            con.close();
            return 1;
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return 0;
    }
 
    public int save() {
    Connection con = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
        if (this.id > 0) { // Actualizar registro existente
            PreparedStatement ps = con.prepareStatement("UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?");
            ps.setString(1, this.nombre);
            ps.setString(2, this.descripcion);
            ps.setInt(3, this.id);
            ps.executeUpdate();
        } else { // Insertar nuevo registro
            PreparedStatement ps = con.prepareStatement("INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.nombre);
            ps.setString(2, this.descripcion);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1); // Obtener el ID generado automáticamente
            }
        }
        con.close();
        return 1;
    } catch (SQLException e) {
        System.out.println("Error de SQL: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("Error de carga del driver: " + e.getMessage());
    }
    return 0;
}
    

    
    private int delete(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM categoria");
            for(int i = 0; i < listaCategoria.size(); i++) {
                if(listaCategoria.get(i).getId() == id) {
                    listaCategoria.remove(i);
                    break;
                }
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
