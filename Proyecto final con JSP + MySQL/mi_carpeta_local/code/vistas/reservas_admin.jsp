<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Reserva, java.util.List, java.util.Map, java.util.HashMap" %>
<%
    List<Reserva> reservas = (List<Reserva>) request.getAttribute("reservas");

    // Resumen de estados
    Map<String, Integer> resumenEstados = new HashMap<>();
    for (Reserva res : reservas) {
        String estado = res.getEstado();
        resumenEstados.put(estado, resumenEstados.getOrDefault(estado, 0) + 1);
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reservas - Administrador</title>
    <link rel="stylesheet" href="/css/reserva.css">
    <script src="https://kit.fontawesome.com/a2e0cfb69f.js" crossorigin="anonymous"></script>
</head>
<body>

<div class="container">
    <!-- Menú superior -->
    <nav class="navbar">
        <div class="logo">
            <img src="/img/hotel-lisa2.png" alt="Logo Hotel Lisa">
        </div>
        <ul class="nav-links">
            <li><a href="index.jsp?do=user/principal">Inicio</a></li>
            <li><a href="index.jsp?do=user/habitaciones">Habitaciones</a></li>
            <li><a href="index.jsp?do=user/reservas" class="active">Administrar</a></li>
            <li><a href="index.jsp?do=user/inicio">Cerrar Sesión</a></li>
        </ul>
    </nav>

    <!-- Buscador -->
    <div class="search-bar">
        <form method="get" action="index.jsp">
            <input type="hidden" name="do" value="user/buscar" />
            <input type="text" name="buscar" placeholder=" nombre, habitación, número, grupo..." />
            <button type="submit">Buscar</button>
        </form>
    </div>

    <!-- Contenido principal -->
    <div class="reservas-wrapper">
        <!-- Tabla de reservas -->
        <div class="tabla-principal">
            <h2>Listado de Reservas</h2>
            <table>
                <thead>
                    <tr>
                        <th>Reserva</th>
                        <th>Cliente/Huéspedes</th>
                        <th>Habitacion</th>
                        <th>F. Reserva</th>
                        <th>F. Entrada</th>
                        <th>F. Salida</th>
                        <th>Precio</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Reserva res : reservas) {
                %>
                    <tr>
                        <td><%= res.getIdReserva() %></td>
                        <td><%= res.getCliente().getNombre() %> <%= res.getCliente().getApellidos() %> / <%= res.getHuespedes()%> </td>
                        <td><%= res.getHabitacion().getTipo() %> <%= res.getHabitacion().getNumero() %></td>
                        <td><%= res.getCreatedAt() %></td>
                        <td><%= res.getFechaEntrada() %></td>
                        <td><%= res.getFechaSalida() %></td>
                        <td><%= res.getPrecioTotal() %>€</td>
                        <td><span class="estado <%= (res.getEstado() != null ? res.getEstado().toLowerCase() : "sin-estado") %>">
                            <%= (res.getEstado() != null ? res.getEstado() : "Sin estado") %>
                        </span></td>

                       <td>
                        <form  action="index.jsp?do=user/editarReserva" method="post" style="display:inline;">
                            <input type="hidden" name="idReserva" value="<%= res.getIdReserva() %>" />
                            <button type="submit" title="Editar">
                                <svg stroke-linejoin="round" stroke-linecap="round" fill="none" stroke-width="2" stroke="#FFFFFF" height="24" width="24" viewBox="0 0 24 24">
                                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                </svg>
                                Editar
                            </button>
                        </form>

                        <form action="index.jsp?do=user/eliminarReserva" method="post" style="display:inline;" onsubmit="return confirm('¿Seguro que quieres eliminar esta reserva?');">
                            <input type="hidden" name="idReserva" value="<%= res.getIdReserva() %>" />
                            <button type="submit" title="Eliminar">
                                <svg fill="none" stroke="#FFFFFF" stroke-width="2" viewBox="0 0 24 24" height="24" width="24">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M19 7l-.867 12.142A2 2 0 0 1 16.138 21H7.862a2 2 0 0 1-1.995-1.858L5 7m5 4v6m4-6v6M9 7h6m-6 0V5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v2"></path>
                                </svg>
                                Eliminar
                            </button>
                        </form>

                      
                    </td>

                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <!-- Tabla de resumen -->
        <div class="resumen-estados">
            <h2>Resumen de Estados</h2>
            <table>
                <tr><th>Estado</th><th>Cantidad</th></tr>
                <%
                    for (Map.Entry<String, Integer> entry : resumenEstados.entrySet()) {
                %>
                <tr>
                    <td><%= entry.getKey() %></td>
                    <td><%= entry.getValue() %></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
</div>

</body>
</html>
