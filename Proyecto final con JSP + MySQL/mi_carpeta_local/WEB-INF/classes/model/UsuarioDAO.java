package model;
import java.sql.*;

/**
 * Clase UsuarioDAO
 * ----------------
 * Gestiona las operaciones relacionadas con la tabla 'usuario' en la base de datos del hotel.
 * Incluye autenticación, verificación de existencia, registro de nuevos usuarios, 
 * y obtención de roles e identificadores.
 * 
 * Nota: Utiliza conexión directa con MySQL. Se recomienda manejar el cifrado de contraseñas
 * en producción y evitar almacenar contraseñas planas.
 */
public class UsuarioDAO {
   
    // Consultas SQL preparadas
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM usuario WHERE username = ? AND password = ?";
    private static final String INSERT_USER = "INSERT INTO usuario(nombre,apellidos, username, password, rol, email, telefono) VALUES (?, ?, ?,?,?, ?,?)";
    private static final String CHECK_USER_EXISTS = "SELECT * FROM usuario WHERE username = ?";
    
    /**
     * Establece y devuelve una conexión a la base de datos.
     * @return conexión JDBC con la base de datos hotel_reservas
     * @throws SQLException si hay errores al conectarse
     */
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Datos de conexión
        String url = "jdbc:mysql://mysql:3306/hotel_reservas";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Devuelve el rol del usuario si las credenciales son correctas.
     * @param username nombre de usuario
     * @param password contraseña en texto plano
     * @return rol del usuario si existe, null si no se encuentra
     * @throws ClassNotFoundException si no se encuentra el driver JDBC
     */
    public String obtenerRolUsuario(String username, String password) throws ClassNotFoundException {
        String rol = null;
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USERNAME);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                rol = rs.getString("rol");  // Aquí obtenemos el rol
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    /**
     * Verifica si ya existe un usuario con el nombre de usuario proporcionado.
     * @param username nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     * @throws ClassNotFoundException si no se encuentra el driver JDBC
     */
    public boolean existeUsuario(String username) throws ClassNotFoundException {
        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_USER_EXISTS);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Registra un nuevo usuario en la base de datos.
     * @param nombre nombre del usuario
     * @param apellidos apellidos del usuario
     * @param username nombre de usuario (debe ser único)
     * @param password contraseña en texto plano (debe ser cifrada en producción)
     * @param rol rol del usuario (por defecto 'cliente')
     * @param email email del usuario
     * @param telefono teléfono del usuario
     * @throws ClassNotFoundException si no se encuentra el driver JDBC
     */
    
    public void registrarUsuario(String nombre, String apellidos, String username, String password, String rol, String email, String telefono) throws ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            System.out.println("Intentando registrar usuario: " + username);
            connection = getConnection();
            connection.setAutoCommit(false);
            
            // Verificar longitud de datos
            if (username.length() > 50 || password.length() > 50 || nombre.length() > 50 || apellidos.length() > 100) {
                throw new SQLException("Los datos exceden el tamaño máximo permitido");
            }
            
            statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, rol); // Asignar rol por defecto
            statement.setString(6, email);
            statement.setString(7, telefono);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo registrar el usuario, ninguna fila afectada");
            }
            
            connection.commit();
            System.out.println("Usuario registrado exitosamente");
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage(), e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    /**
     * Obtiene el ID del usuario a partir de su nombre de usuario.
     * @param username nombre de usuario
     * @return ID del usuario si se encuentra, -1 si no existe
     */
    public int getIdPorUsername(String username) {
        String sql = "SELECT id_usuario FROM usuario WHERE username = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Devuelve -1 si no se encuentra el usuario
    }

}
