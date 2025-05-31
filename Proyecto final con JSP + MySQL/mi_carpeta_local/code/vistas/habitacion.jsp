<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Habitacion" %>
<%@ page import="model.Reserva" %>
<%
    Habitacion habitacion = (Habitacion) request.getAttribute("habitacion");
    if (habitacion == null) {
        response.sendRedirect("index.jsp?do=user/habitaciones");
        return;
    }
    
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hotel Lisa - <%= habitacion.getTipo() %></title>
        <link rel="stylesheet" href="/css/habitacion.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&family=Playfair+Display:wght@600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <body>
        <div class="habitacion-container">
            <!-- Header con navegación -->
            <header class="habitacion-header">
                <div class="logo">
                    <img src="/img/hotel-lisa2.png" alt="Hotel Lisa">
                </div>
                <nav>
                    <ul>
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
                            <li><a href="index.jsp?do=user/reservas_cliente">Mis Reservas</a></li>
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
                </nav>
            </header>

            <!-- Galería de imágenes -->
            <section class="habitacion-gallery">
                <div class="main-image">
                    <img src="<%= habitacion.getImagen() != null ? habitacion.getImagen() : "/img/default-room.jpg" %>" alt="<%= habitacion.getTipo() %>">
                </div>
                <div class="thumbnail-container">
                    <div class="thumbnail active"><img src="<%= habitacion.getImagen() %>" alt="Vista 1"></div>
                
                </div>
            </section>

            <!-- Detalles de la habitación -->
            <section class="habitacion-details">
                <div class="details-content">
                    <h1><%= habitacion.getTipo() %></h1>
                    <div class="rating">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star-half-alt"></i>
                        <span>4.7 (128 reseñas)</span>
                    </div>
                    
                    <div class="price-tag">
                        <span class="price"><%= habitacion.getPrecioNoche() %>€</span>
                        <span class="per-night">por noche</span>
                    </div>
                    
                    <div class="amenities">
                        <h2>Servicios de la habitación</h2>
                        <ul>
                            <li><i class="fas fa-wifi"></i> WiFi gratuito</li>
                            <li><i class="fas fa-utensils"></i> Desayuno buffet incluido</li>
                            <li><i class="fas fa-tv"></i> TV pantalla plana 42"</li>
                            <li><i class="fas fa-snowflake"></i> Aire acondicionado</li>
                            <li><i class="fas fa-hot-tub"></i> Bañera hidromasaje</li>
                            <li><i class="fas fa-parking"></i> Parking gratuito</li>
                        </ul>
                    </div>
                    
                    <div class="description">
                        <h2>Descripción</h2>
                        <p><%= habitacion.getDescripcion() %></p>
                        <p>Disfrute de nuestra exclusiva habitación <%= habitacion.getTipo() %> con vistas panorámicas a la ciudad. 
                        Ropa de cama de alta calidad y amenities de lujo incluidos.</p>
                    </div>
                </div>
                
                <!-- Formulario de reserva -->
                <div class="booking-form-container">
                    <div class="form-header">
                        <h2>Reservar ahora</h2>
                      
                    </div>
                    
                   <form id="reservaForm" action="index.jsp?do=user/guardarReserva" method="POST">
    <input type="hidden" name="idHabitacion" value="<%= habitacion.getIdHabitacion() %>">
    
    <div class="form-group">
        <label for="fechaEntrada">Fecha de entrada</label>
        <input type="date" id="fechaEntrada" name="fechaEntrada" required>
    </div>
    
    <div class="form-group">
        <label for="fechaSalida">Fecha de salida</label>
        <input type="date" id="fechaSalida" name="fechaSalida" required>
    </div>
    
    <div class="form-group">
        <label for="huespedes">Huéspedes</label>
        <select id="huespedes" name="huespedes" required>
            <% for (int i = 1; i <= habitacion.getCapacidad(); i++) { %>
                <option value="<%= i %>"><%= i %> <%= i == 1 ? "persona" : "personas" %></option>
            <% } %>
        </select>
    </div>
    
    <button type="submit" class="btn-reservar">Reservar ahora <i class="fas fa-arrow-right"></i></button>
</form>
                </div>
            </section>
            
            <!-- Sección de recomendaciones -->
            <section class="recommendations">
                <h2>Otras habitaciones que te pueden gustar</h2>
                <div class="rooms-grid">
                    <!-- Aquí irían otras habitaciones recomendadas -->
                </div>
            </section>
        </div>
        
        <script src="/js/habitacion.js"></script>
    </body>
</html>