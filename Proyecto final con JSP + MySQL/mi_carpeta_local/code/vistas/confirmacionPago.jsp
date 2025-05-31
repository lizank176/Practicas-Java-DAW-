<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Reserva" %>
<%
    Reserva reserva = (Reserva) request.getAttribute("reserva");
    String mensaje = (String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmación de Pago</title>
    <link rel="stylesheet" href="/css/estilos.css">
</head>
<body>
    <div class="confirmacion-container">
        <h1><%= mensaje %></h1>
        <% if (reserva != null) { %>
            <div class="reserva-info">
                <h2>Detalles de tu reserva</h2>
                <p>Número de reserva: <%= reserva.getIdReserva() %></p>
                <p>Fecha de entrada: <%= reserva.getFechaEntrada() %></p>
                <p>Fecha de salida: <%= reserva.getFechaSalida() %></p>
                <p>Total pagado: $<%= reserva.getPrecioTotal() %></p>
            </div>
        <% } %>
        <a href="index.jsp?do=user/reservas" class="btn">Ver mis reservas</a>
    </div>
</body>
</html>