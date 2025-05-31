<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Reserva" %>
<%@ page import="model.Habitacion" %>
<%@ page import="java.util.List" %>
<%
    Reserva reserva = (Reserva) request.getAttribute("reserva");
    List<Habitacion> habitaciones = (List<Habitacion>) request.getAttribute("habitaciones");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Reserva</title>
    <link rel="stylesheet" href="/css/editar.css">
</head>
<body>
    <div class="container">
        <h2>Editar Reserva</h2>
        <form action="index.jsp?do=user/guardarCambios" method="post">
            <input type="hidden" name="idReserva" value="<%= reserva.getIdReserva() %>">
            <input type="hidden" name="idHabitacion" value="<%= reserva.getHabitacion().getIdHabitacion() %>">


            <label>Fecha Entrada:</label>
            <input type="date" name="fechaEntrada" value="<%= reserva.getFechaEntrada() %>" required><br>

            <label>Fecha Salida:</label>
            <input type="date" name="fechaSalida" value="<%= reserva.getFechaSalida() %>" required><br>

            <label>Precio Total:</label>
            <input type="number" name="precioTotal" step="0.01" value="<%= reserva.getPrecioTotal() %>" required><br>

            <!-- Campo corregido para número de huéspedes -->
            <label>Huéspedes:</label>
            <input type="number" name="numHuespedes" value="<%= reserva.getHuespedes() %>" required><br>
            

            <label>Estado:</label>
            <select name="estado">
                <option value="Confirmada" <%= "Confirmada".equals(reserva.getEstado()) ? "selected" : "" %>>Confirmada</option>
                <option value="Pendiente" <%= "Pendiente".equals(reserva.getEstado()) ? "selected" : "" %>>Pendiente</option>
                <option value="Cancelada" <%= "Cancelada".equals(reserva.getEstado()) ? "selected" : "" %>>Cancelada</option>
            </select><br>

            <button type="submit">Guardar Cambios</button>
        </form>
    </div>
</body>
</html>