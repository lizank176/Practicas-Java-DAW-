package com.iescelia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Evento {
    private String nombre;
    private int id;
    private String lugar;
    private String fecha_inicio;
    private String fecha_fin;
    private String descripcion;
    private int id_categoria;
    

    public static ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
    public Evento(int id, String nombre, String lugar, String fecha_inicio, String fecha_fin, String descripcion, int id_categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha_inicio= fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.id_categoria = id_categoria;
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
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
   
    public void setFechaInicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
    public String getFechaFin() {
        return fecha_fin;
    }
    public String getFechaInicio() {
        return fecha_inicio;
    }
    public void setHora(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
    public String getDescripcionEvento() {
        return descripcion;
    }
    public void setDescripcionEvento(String descripcion) {
        this.descripcion = descripcion;
    }
   

    public int getId_categoria() {
        return id_categoria;
    }
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    public String toString() {
        return id + " - " + nombre + " - " + lugar + " - " + fecha_inicio + " - " + fecha_fin + " - " + descripcion+ " - " + id_categoria;
    }
    
    public static void  getAll() {
        Connection con = null;
        listaEventos.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM evento");
            while (rs.next()) {
                Evento u = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getString("descripcion"), rs.getInt("id_categoria"));
                listaEventos.add(u);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }  
        
    }
   
    public Evento getById(int id) {
        Connection con = null;
        Evento u = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM evento");
            while (rs.next()) {
                if(rs.getInt("id") == id) {
                    u = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("lugar"), rs.getString("fecha"), rs.getString("hora"), rs.getString("descripcion"), rs.getInt("id_categoria"));
                    return u;
                }
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }  
        return u;
    }
   
    public static void getByNombre(String txt) {
        Connection con = null;
        listaEventos.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM evento WHERE nombre LIKE '%" + txt + "%' OR  descripcion LIKE '%" + txt + "%'");
            while (rs.next()) {
                Evento u = new Evento(rs.getInt("id"), rs.getString("nombre"), rs.getString("lugar"), rs.getString("fecha_inicio"), rs.getString("fecha_fin"), rs.getString("descripcion"), rs.getInt("id_categoria"));
                listaEventos.add(u);
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
            ResultSet rs = st.executeQuery("SELECT MAX(id) AS maximo FROM evento");
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
   
 
    public int  save() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM evento WHERE id = " + this.getId());
            // Si existe el evento, actualizamos, si no, insertamos
            if (rs.next()) {
                String updateQuery = "UPDATE evento SET nombre = '" + nombre + "', lugar = '" + lugar + 
                     "', fecha_inicio = '" + fecha_inicio + "', fecha_fin = '" + fecha_fin + 
                     "', descripcion = '" + descripcion + "', id_categoria = '" + id_categoria + 
                     "' WHERE id = '" + this.getId() + "'";

                System.out.println(updateQuery);
                st.executeUpdate(updateQuery);
            } else {
                String insertQuery = "INSERT INTO evento (id, nombre, lugar, fecha_inicio, fecha_fin, descripcion, id_categoria) VALUES (" + id + ", '" + nombre + "', '" + lugar + "', '" + fecha_inicio + "', '" + fecha_fin + "', '" + descripcion + "', '"+id_categoria+"')";
                System.out.println(insertQuery);
                st.executeUpdate(insertQuery);
            }
            con.close();
            return 1;
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return 0;
    }

    public int delete(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM evento");
            for(int i = 0; i < listaEventos.size(); i++) {
                if(listaEventos.get(i).getId() == id) {
                    listaEventos.remove(i);
                    break;
                }
            }
            String deleteQuery = "DELETE FROM evento WHERE id = " + id;
            int result = st.executeUpdate(deleteQuery);
            con.close();
            return 1;
        }
        catch(Exception e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return 0;
    }

    public Categoria getCategoria(){
        Categoria categoria = Categoria.getByIdCategoria( this.id_categoria);
        return categoria;    
    }
    
    public String getNombreCategoria() {
        String nombreCategoria = "Desconocida";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
             PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM categoria WHERE id = ?")) {
            
            stmt.setInt(1, this.id_categoria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombreCategoria = rs.getString("nombre");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el nombre de la categoría: " + e.getMessage());
        }
        return nombreCategoria;
    }
    

    public static  ObservableList<String> cargarCategorias() {
        ObservableList<String> listaCategorias = FXCollections.observableArrayList();
        listaCategorias.clear();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre FROM categoria")) {
            while (rs.next()) {
                listaCategorias.add(rs.getString("nombre"));
            }         
        } catch (Exception e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
        }
        return listaCategorias; // Siempre devolvemos la lista, vacía o llena
    }

    
}
