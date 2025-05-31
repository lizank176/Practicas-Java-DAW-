package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para la entidad Habitacion.
 * 
 * Proporciona métodos para acceder y manipular datos de habitaciones en la base de datos.
 * Incluye operaciones para obtener habitaciones por id, listar todas las habitaciones
 * y buscar habitaciones aplicando varios filtros.
 */
public class HabitacionDAO {
    /** Consulta SQL para obtener todas las habitaciones */
    private static final String SELECT_ALL = "SELECT * FROM habitaciones";
    /**
     * Establece y devuelve una conexión a la base de datos.
     * @return conexión JDBC con la base de datos hotel_reservas
     * @throws SQLException si hay errores al conectarse
     */
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://mysql:3306/hotel_reservas";
        String user = "root";
        String password = "";  // Cambia si tienes password
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Obtiene una habitación por su ID.
     * @param idHabitacion ID de la habitación a buscar
     * @return objeto Habitacion si se encuentra, null en caso contrario
     */
    public Habitacion obtenerHabitacionPorId(int idHabitacion){
        String sql = "SELECT * FROM habitaciones WHERE id_habitacion = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idHabitacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("id_habitacion"));
                h.setTipo(rs.getString("tipo"));
                h.setDescripcion(rs.getString("descripcion"));
                h.setPrecioNoche(rs.getDouble("precio_noche"));
                h.setCapacidad(rs.getInt("capacidad"));
                h.setDisponible(rs.getBoolean("disponible"));
                h.setImagen(rs.getString("imagen"));
                h.setNumero(rs.getInt("numero")); // Asumiendo que tienes un campo "numero" en la tabla
                return h;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas las habitaciones disponibles en la base de datos.
     * @return lista de objetos Habitacion
     */
    public List<Habitacion> listarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habitacion h = new Habitacion();
                h.setIdHabitacion(rs.getInt("id_habitacion"));
                h.setTipo(rs.getString("tipo"));
                h.setDescripcion(rs.getString("descripcion"));
                h.setPrecioNoche(rs.getDouble("precio_noche"));  // doble en vez de BigDecimal
                h.setCapacidad(rs.getInt("capacidad"));
                h.setDisponible(rs.getBoolean("disponible"));
                h.setImagen(rs.getString("imagen"));
                h.setNumero(rs.getInt("numero")); // Asumiendo que tienes un campo "numero" en la tabla
                habitaciones.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habitaciones;
    }
     

}
