<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Reserva" %>

<%
// Verifica si la reserva está disponible en el request
    Reserva reserva = (Reserva) request.getAttribute("reserva");
     if (reserva == null) {
        response.sendRedirect("index.jsp?do=user/reservas");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Pagar Reserva</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/pago.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<body>
<div class="card-container">
    <div class="credit-card">
        <div class="number" id="card-number">•••• •••• •••• ••••</div>
        <div class="name" id="card-name">NOMBRE DEL TITULAR</div>
        <div class="expiry" id="card-expiry">MM/AA</div>
    </div>

    <form method="post" action="index.jsp?do=user/confirmarPago">
        <input type="hidden" name="reservaId" value="<%= reserva.getIdReserva() %>">
        <input type="text" name="nombreTitular" placeholder="Nombre del titular" required oninput="updateName(this.value)">
        <input type="text" name="numeroTarjeta" placeholder="Número de tarjeta" required maxlength="19" oninput="updateNumber(this.value)">
        <input type="text" name="fechaExpiracion" placeholder="Fecha de expiración (MM/AA)" required maxlength="5" oninput="updateExpiry(this.value)">
        <input type="text" name="cvv" placeholder="CVV" required maxlength="4">
        <div class="total">
            <p>Total a pagar: <strong><%= reserva.getPrecioTotal() %> €</strong></p>
        </div>
        <div class="button-group">
            <button type="submit" class="btn-primary">
                <i class="fas fa-credit-card" style="margin-right: 8px;"></i> Pagar ahora
            </button>
            <a href="index.jsp?do=user/pagarMasTarde&idReserva=<%= reserva.getIdReserva() %>" class="btn-secondary" style="text-decoration: none;">
                <i class="far fa-clock" style="margin-right: 8px;"></i> Pagar más tarde
            </a>

        </div>
    </form>
</div>

<script>
// Actualiza el contenido de la tarjeta en tiempo real
    function updateName(value) {
        document.getElementById("card-name").textContent = value.toUpperCase() || "NOMBRE DEL TITULAR";
    }
// Actualiza el número de tarjeta con formato
    function updateNumber(value) {
        const formatted = value.replace(/\D/g, '').replace(/(.{4})/g, '$1 ').trim();
        document.getElementById("card-number").textContent = formatted || "•••• •••• •••• ••••";
    }
// Actualiza la fecha de expiración con formato MM/AA
    function updateExpiry(value) {
        document.getElementById("card-expiry").textContent = value || "MM/AA";
    }
</script>
</body>
</html>