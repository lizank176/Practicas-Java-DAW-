package model;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * Clase modelo que representa una Reserva en el sistema de gestión hotelera.
 * 
 * Esta clase contiene los atributos que definen una reserva, como:
 * - Información del cliente (usuario)
 * - Información de la habitación reservada
 * - Fechas de entrada y salida
 * - Precio total y anticipo pagado
 * - Estado de la reserva (confirmada, cancelada, pendiente, etc.)
 * - Observaciones adicionales
 * - Fecha y hora de creación
 * - Número de huéspedes
 * - Si incluye desayuno o no
 * 
 * Se utiliza para transferir datos entre la capa de persistencia (base de datos)
 * y la lógica o presentación de la aplicación.
 */
public class Reserva {
    private int idReserva;
    private Usuario cliente;
    private Habitacion habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double precioTotal;
    private double anticipo;
    private String estado;
    private String observaciones;
    private Timestamp createdAt;
    private int huespedes;
    private boolean desayuno;
    // Constructor vacío
    public Reserva() {
        this.cliente = new Usuario();
        this.habitacion = new Habitacion();
    }

    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public int getHuespedes() {
        return huespedes;
    }
    public void setHuespedes(int huespedes) {
        this.huespedes = huespedes;
    }
    

    public boolean isDesayuno() {
        return desayuno;
    }
   
   
}
