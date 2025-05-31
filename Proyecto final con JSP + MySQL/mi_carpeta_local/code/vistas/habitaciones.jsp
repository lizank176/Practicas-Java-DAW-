<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.HabitacionDAO" %>
<%@ page import="model.Habitacion" %>
<%@ page import="model.UsuarioDAO" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Lisa - Nuestras Habitaciones</title>
    <link rel="stylesheet" href="/css/habitaciones.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&family=Playfair+Display:wght@600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <!-- Barra de navegación -->
        <nav class="navbar">
            <div class="logo">
                <img src="/img/hotel-lisa2.png" alt="Logo Hotel Lisa">
            </div>
            <ul class="nav-links">
                <li><a href="index.jsp?do=user/principal">Inicio</a></li>
                <li><a href="index.jsp?do=user/habitaciones" class="active">Habitaciones</a></li>
                <%
                    String rol = (String) session.getAttribute("rol");
                    String usuario = (String) session.getAttribute("usuario");

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

   

        <!-- Listado de habitaciones -->
        <div class="rooms-container">
            <div class="rooms-header">
                <h2>Nuestras Habitaciones</h2>
                <p class="results-count"><%
                    List<Habitacion> habitaciones = (List<Habitacion>) request.getAttribute("habitaciones");
                %></p>
            </div>
            
            <div class="rooms-grid">
                <%
                    for (Habitacion habitacion : habitaciones) {
                %>
                
                <div class="room-card"
                    data-tipo="<%= habitacion.getTipo().toLowerCase() %>"
                    data-precio="<%= habitacion.getPrecioNoche() %>"
                    data-capacidad="<%= habitacion.getCapacidad() %>">

                    <div class="room-image" style="background-image: url('<%= habitacion.getImagen() != null ? habitacion.getImagen() : "/img/default-room.jpg" %>')">
                        <% if (habitacion.isDisponible()) { %>
                            <span class="availability available">Disponible</span>
                        <% } else { %>
                            <span class="availability not-available">No disponible</span>
                        <% } %>
                    </div>
                    
                    <div class="room-info">
                        <div class="room-header">
                            <h3><%= habitacion.getTipo() %></h3>
                            <div class="room-rating">
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star"></i>
                                <i class="fas fa-star-half-alt"></i>
                            </div>
                        </div>
                        
                        <p class="room-location">
                            <i class="fas fa-map-marker-alt"></i> Hotel Lisa • Planta <%= (int)(Math.random()*5)+1 %>
                        </p>
                        
                        <p class="room-description"><%= habitacion.getDescripcion() %></p>
                        
                        <div class="room-features">
                            <span><i class="fas fa-user-friends"></i> <%= habitacion.getCapacidad() %> personas</span>
                            <span><i class="fas fa-wifi"></i> WiFi gratis</span>
                            <span><i class="fas fa-utensils"></i> Desayuno</span>
                        </div>
                        
                        <div class="room-footer">
                            <div class="room-price">
                                <span class="price"><%= habitacion.getPrecioNoche() %>€</span>
                                <span class="per-night">por noche</span>
                            </div>
                            
                            <% if (habitacion.isDisponible()) { %>
                                <a href="index.jsp?do=user/habitacion&id=<%= habitacion.getIdHabitacion() %>" class="book-button">
                                    Reservar ahora <i class="fas fa-arrow-right"></i>
                                </a>
                            <% } else { %>
                                <button class="book-button disabled" disabled>
                                    No disponible
                                </button>
                            <% } %>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        
        <!-- Mapa -->
        <div class="map-container">
            <h2><i class="fas fa-map-marked-alt"></i> Ubicación de nuestro hotel</h2>
            <div class="map-placeholder">
                <img src="/img/map-placeholder.jpg" alt="Mapa del hotel">
                <div class="map-overlay">
                    <button class="view-map-button">
                        <i class="fas fa-expand"></i> Ver en mapa completo
                    </button>
                </div>
            </div>
            <p class="map-note">Map data ©2025 Google</p>
        </div>
    </div>
    
    <script src="/js/habitaciones.js"></script>
</body>
</html>