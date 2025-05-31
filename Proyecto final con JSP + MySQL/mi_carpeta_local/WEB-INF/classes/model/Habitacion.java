package model;


/**
 * Clase modelo que representa una habitación en el sistema de reservas del hotel.
 * Contiene los atributos básicos que definen una habitación y sus respectivos
 * métodos getters y setters para acceder y modificar sus propiedades.
 */
public class Habitacion {
    private int idHabitacion;
    private String tipo;
    private String descripcion;
    private double precioNoche;
    private int capacidad;
    private boolean disponible;
    private String imagen;
    private int numero;
    // Getters y Setters
    public int getIdHabitacion() {
        return idHabitacion;
    }
    
    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getPrecioNoche() {
        return precioNoche;
    }
    
    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    
}