package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase DAO (Data Access Object) para gestionar la persistencia y consulta de reservas
 * en la base de datos del sistema de gestión hotelera.
 * 
 * Esta clase incluye métodos para:
 * - Obtener reservas (todas, por usuario, por estado)
 * - Insertar nuevas reservas
 * - Actualizar reservas (completas o solo estado)
 * - Eliminar reservas
 * - Buscar reservas por término de búsqueda (nombre usuario, tipo habitación, etc.)
 * 
 * Utiliza JDBC para conectar con una base de datos MySQL.
 */
public class ReservaDAO {
     /**
     * Establece la conexión con la base de datos MySQL.
     * Se carga el driver JDBC y se conecta a la URL especificada con el usuario y contraseña.
     * 
     * @return Connection objeto de conexión con la base de datos.
     * @throws SQLException en caso de fallo en la conexión.
     */
    private static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://mysql:3306/hotel_reservas";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

     /**
     * Obtiene todas las reservas con toda la información relacionada:
     * datos del usuario cliente y datos de la habitación.
     * Método usado en modo administrador para visualizar todas las reservas.
     * 
     * @return Lista de objetos Reserva con datos completos.
     */
    public List<Reserva> getReservasAdmin() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, u.nombre, u.apellidos, u.username, u.email, u.telefono, " +
                    "h.id_habitacion, h.tipo, h.descripcion, h.precio_noche, h.capacidad, " +
                    "h.disponible, h.imagen, h.numero " +
                    "FROM reservas r " +
                    "JOIN usuario u ON r.id_usuario = u.id_usuario " +
                    "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reservas.add(mapReserva(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    /**
     * Obtiene las reservas de un usuario específico, con información completa
     * de usuario y habitación.
     * 
     * @param idUsuario ID del usuario para filtrar las reservas.
     * @return Lista de reservas del usuario.
     */
    public List<Reserva> getReservasPorUsuario(int idUsuario) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas r " +
                     "JOIN usuario u ON r.id_usuario = u.id_usuario " +
                     "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                     "WHERE r.id_usuario = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapReserva(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    /**
     * Inserta una nueva reserva en la base de datos.
     * 
     * @param reserva Objeto Reserva con los datos a insertar.
     * @return El ID generado de la nueva reserva.
     * @throws SQLException si la inserción falla.
     */
    public int insertarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (id_usuario, id_habitacion, fecha_entrada, fecha_salida, precio_total, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, reserva.getCliente().getId());
            pstmt.setInt(2, reserva.getHabitacion().getIdHabitacion());
            pstmt.setDate(3, new java.sql.Date(reserva.getFechaEntrada().getTime()));
            pstmt.setDate(4, new java.sql.Date(reserva.getFechaSalida().getTime()));
            pstmt.setDouble(5, reserva.getPrecioTotal());
            pstmt.setString(6, reserva.getEstado());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La creación de reserva falló, no se insertaron filas.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devuelve el ID generado
                } else {
                    throw new SQLException("La creación de reserva falló, no se obtuvo ID.");
                }
            }
        }
    }
    
    /**
     * Mapea un ResultSet obtenido de una consulta a un objeto Reserva completo.
     * También mapea el usuario cliente y la habitación asociada.
     * 
     * @param rs ResultSet de la consulta con la fila actual posicionada.
     * @return Objeto Reserva con todos los datos mapeados.
     * @throws SQLException si ocurre un error al acceder a los datos del ResultSet.
     */
    private Reserva mapReserva(ResultSet rs) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("id_reserva"));

        // Usuario
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setUsername(rs.getString("username"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefono(rs.getString("telefono"));
        reserva.setCliente(usuario);

        // Habitación
        Habitacion habitacion = new Habitacion();
        habitacion.setIdHabitacion(rs.getInt("id_habitacion"));
        habitacion.setTipo(rs.getString("tipo"));
        habitacion.setDescripcion(rs.getString("descripcion"));
        habitacion.setPrecioNoche(rs.getDouble("precio_noche"));
        habitacion.setCapacidad(rs.getInt("capacidad"));
        habitacion.setDisponible(rs.getBoolean("disponible"));
        habitacion.setImagen(rs.getString("imagen"));
        habitacion.setNumero(rs.getInt("numero")); // Asumiendo que tienes un campo "numero" en la tabla
        reserva.setHabitacion(habitacion);

        // Otros campos
        reserva.setFechaEntrada(rs.getDate("fecha_entrada"));
        reserva.setFechaSalida(rs.getDate("fecha_salida"));
        reserva.setPrecioTotal(rs.getDouble("precio_total"));
        reserva.setAnticipo(rs.getDouble("anticipo"));
        reserva.setEstado(rs.getString("estado"));
        reserva.setObservaciones(rs.getString("observaciones"));
        reserva.setCreatedAt(rs.getTimestamp("created_at"));
        reserva.setHuespedes(rs.getInt("huespedes"));
        return reserva;
    }
    /**
     * Obtiene una reserva por su ID, incluyendo información de usuario y habitación.
     * 
     * @param idReserva ID de la reserva a obtener.
     * @return Objeto Reserva completo o null si no existe.
     */
    public Reserva getReservaPorId(int idReserva) {
        String sql = "SELECT * FROM reservas r " +
                    "JOIN usuario u ON r.id_usuario = u.id_usuario " +
                    "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                    "WHERE r.id_reserva = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapReserva(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza los datos completos de una reserva.
     * 
     * @param reserva Objeto Reserva con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reservas SET id_usuario = ?, id_habitacion = ?, fecha_entrada = ?, fecha_salida = ?, precio_total = ?, huespedes=?, estado = ? " +
                     "WHERE id_reserva = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, reserva.getCliente().getId());
            stmt.setInt(2, reserva.getHabitacion().getIdHabitacion());
            stmt.setDate(3, reserva.getFechaEntrada());
            stmt.setDate(4, reserva.getFechaSalida());
            stmt.setDouble(5, reserva.getPrecioTotal());
            stmt.setInt(6, reserva.getHuespedes());
            stmt.setString(7, reserva.getEstado());
            stmt.setInt(8, reserva.getIdReserva());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Actualiza solo el estado de una reserva.
     * 
     * @param idReserva ID de la reserva a actualizar.
     * @param nuevoEstado Nuevo estado a establecer.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id_reserva = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idReserva);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una reserva por su ID.
     * 
     * @param idReserva ID de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarReserva(int idReserva) {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las reservas filtradas por estado.
     * 
     * @param estado Estado de las reservas a filtrar (ej. "Confirmada", "Pendiente").
     * @return Lista de reservas que coinciden con el estado.
     */
    public List<Reserva> getReservasPorEstado(String estado) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas r " +
                     "JOIN usuario u ON r.id_usuario = u.id_usuario " +
                     "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                     "WHERE r.estado = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, estado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapReserva(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
    
    /**
     * Obtiene las reservas confirmadas de un usuario específico.
     * 
     * @param idUsuario ID del usuario para filtrar las reservas confirmadas.
     * @return Lista de reservas confirmadas del usuario.
     */
    public List<Reserva> getReservasConfirmadasPorUsuario(int idUsuario) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE id_usuario = ? AND estado = 'Confirmada'";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                // Mapear los datos del ResultSet al objeto Reserva
                reserva.setIdReserva(rs.getInt("id_reserva"));
                // ... resto del mapeo ...
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    /**
     * Obtiene las reservas pendientes de un usuario específico.
     * 
     * @param idUsuario ID del usuario para filtrar las reservas pendientes.
     * @return Lista de reservas pendientes del usuario.
     */
    public List<Reserva> getReservasPendientesPorUsuario(int idUsuario) {
        List<Reserva> lista = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM reservas WHERE id_usuario = ? AND estado = 'pendiente'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva r = new Reserva();
                // aquí setear los datos de la reserva desde el ResultSet
                lista.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Guarda los cambios realizados en una reserva existente.
     * 
     * @param reserva Objeto Reserva con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean guardarCambios(Reserva reserva) {
            String sql = "UPDATE reservas SET  id_habitacion = ?, fecha_entrada = ?, fecha_salida = ?, precio_total = ?, huespedes=?, estado = ? " +
                        "WHERE id_reserva = ?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1, reserva.getHabitacion().getIdHabitacion());
                stmt.setDate(2, reserva.getFechaEntrada());
                stmt.setDate(3, reserva.getFechaSalida());
                stmt.setDouble(4, reserva.getPrecioTotal());
                stmt.setDouble(5, reserva.getHuespedes());
                stmt.setString(6, reserva.getEstado());
                stmt.setInt(7, reserva.getIdReserva());

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    
    /**
     * Busca reservas en la base de datos según un término de búsqueda.
     * Permite buscar por nombre de usuario, apellidos, número de habitación o tipo de habitación.
     * @param termino
     * @return
     */
      public List<Reserva> buscarReservas(String termino) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.*, " +
                    "u.nombre, u.apellidos, u.username, u.email, u.telefono, " +
                    "h.numero, h.tipo, h.descripcion, h.precio_noche, h.capacidad, h.disponible, h.imagen " +
                    "FROM reservas r " +
                    "JOIN usuario u ON r.id_usuario = u.id_usuario " +
                    "JOIN habitaciones h ON r.id_habitacion = h.id_habitacion " +
                    "WHERE LOWER(u.nombre) LIKE ? " +
                    "OR LOWER(u.apellidos) LIKE ? " +
                    "OR LOWER(CAST(h.numero AS CHAR)) LIKE ? " +
                    "OR LOWER(h.tipo) LIKE ?";

        try (Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            String filtro = "%" + termino.toLowerCase() + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reserva reserva = mapReserva(rs);
                lista.add(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

   
}


