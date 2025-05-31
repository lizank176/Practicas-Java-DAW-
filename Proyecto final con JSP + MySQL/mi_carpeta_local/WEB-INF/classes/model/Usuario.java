package model;
/**
 * Clase Usuario
 * -------------
 * Modelo que representa a un usuario en el sistema.
 * Contiene los atributos básicos que corresponden a las columnas de la tabla 'usuario' en la base de datos.
 * Incluye getters y setters para acceder y modificar los datos del usuario.
 */
public class Usuario {
     private int id;
     private String nombre;
     private String email;
     private String apellidos;
     private String password;
     private String username;
     private String rol;
     private String telefono;

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

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getApellidos() {
          return apellidos;
     }

     public void setApellidos(String apellidos) {
          this.apellidos = apellidos;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }
     public String getUsername() {
          return username;
     }
     public void setUsername(String username) {
          this.username = username;
     }
     public String getRol() {
          return rol;
     }
     public void setRol(String rol) {
          this.rol = rol;
     }
     public String getTelefono() {
          return telefono;
     }
     public void setTelefono(String telefono) {
          this.telefono = telefono;
     }
     

}
