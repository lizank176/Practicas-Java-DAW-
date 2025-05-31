<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Reserva" %>
<%@ page import="model.Habitacion" %>
<%
    List<Reserva> reservas = (List<Reserva>) request.getAttribute("reservas");
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("index.jsp?do=user/inicio");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Reservas - Hotel Lisa</title>
    <link rel="stylesheet" href="/css/reserva_cliente.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
 <!-- Menú de navegación -->
    <nav class="navbar">
        <div class="logo">
            <img src="/img/hotel-lisa2.png" alt="Logo Hotel Lisa">
        </div>
        <ul class="nav-links">
            <li><a href="index.jsp?do=user/principal">Inicio</a></li>
            <li><a href="index.jsp?do=user/habitaciones">Habitaciones</a></li>

            <%
                String rol = (String) session.getAttribute("rol");

                if ("admin".equals(rol)) {
            %>
                    <li><a href="index.jsp?do=user/reservas">Administrar</a></li>
            <%
                } else  {
            %>
                    <li><a href="index.jsp?do=user/reservas">Mis Reservas</a></li>
            <%
                }
            %>

            <%
                if (usuario != null) {
            %>
                    <li><a href="index.jsp?do=user/inicio">Cerrar Sesión</a></li>
            <%
                }
            %>
        </ul>
        <div class="burger">
            <i class="fas fa-bars"></i>
        </div>
    </nav>
<body>
    <div class="reservas-container">
        <h1>Mis Reservas</h1>
    <form action="index.jsp?do=user/pagar" method="post">
        <% if (reservas != null && !reservas.isEmpty()) { %>
            <div class="grid-reservas">
                <% for (Reserva r : reservas) { 
                    Habitacion h = r.getHabitacion(); %>
                    <div class="reserva-card">
                        <img src="<%= h.getImagen() %>" alt="Habitación <%= h.getTipo() %>">
                        <div class="reserva-info">
                     
                            <h2><%= h.getTipo() %></h2>
                            <p><i class="fas fa-calendar-alt"></i> Entrada: <%= r.getFechaEntrada() %></p>
                            <p><i class="fas fa-calendar-check"></i> Salida: <%= r.getFechaSalida() %></p>
                            <p><i class="fas fa-user-friends"></i> Huéspedes: <%= r.getHuespedes() %></p>
                            <p><i class="fas fa-coffee"></i> Desayuno incluido: <%= r.isDesayuno() ? "Incluido" : "No incluido" %></p>
                            <p><i class="fas fa-euro-sign"></i> Precio total: <%= r.getPrecioTotal() %>€</p>
                           <div class="reserva-info">
                           <p>Estado: <%= r.getEstado() %></p>
                            <% if ("Pendiente".equals(r.getEstado())) { %>
                                <form action="index.jsp?do=user/pagar" method="post">
                                    <input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                                    <button class="Btn" type="submit">
                                        Pagar
                                        <svg class="svgIcon" viewBox="0 0 576 512"><!-- ... --></svg>
                                    </button>
                                </form>
                            <% } %>
                        </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <p class="no-reservas">Aún no tienes reservas realizadas.</p>
        <% } %>
    </div>
    </form>
</body>
</html>

