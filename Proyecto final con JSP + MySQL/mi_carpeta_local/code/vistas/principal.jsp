<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hotel Lisa - Lujo y Confort</title>
        <link rel="stylesheet" href="/css/index.css">
        <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    
    </head>
     <body>
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

            <header class="hero">
            
                <div class="hero-content">
            
                    <h1>Bienvenido al Hotel Lisa</h1>
                    <p>Experimenta el lujo en su máxima expresión</p>
                <div class="reservar-wrapper">
                    <button class="reservar-btn" onclick="location.href='index.jsp?do=user/habitaciones'">
                        Reservar
                        <div class="star-1">
                        <!-- SVG estrella -->
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 784.11 815.53">
                            <path class="fil0" d="M392.05 0c-20.9,210.08 -184.06,378.41 -392.05,407.78 207.96,29.37 371.12,197.68 392.05,407.74 20.93,-210.06 184.09,-378.37 392.05,-407.74 -207.98,-29.38 -371.16,-197.69 -392.06,-407.78z" />
                        </svg>
                        </div>
                        <!-- Puedes copiar y pegar el resto de estrellas 2 a 6 igual que esta -->
                    </button>
                    </div>


                </div>
            

            </header>

            <!-- Sección de Habitaciones -->
            <section class="habitaciones">
                <h2>Nuestras Habitaciones</h2>
                <div class="habitaciones-grid">
                    <div class="habitacion-card">
                        <!-- Reemplazar con imagen real: img/habitacion1.jpg -->
                        <img src="/img/estandar1.jpg" alt="Habitación Standard">
                        <h3>Habitación Standard</h3>
                        <p>Perfecta para una estancia cómoda y acogedora.</p>
                        <a href="index.jsp?do=user/habitacion&id=1" class="btn-secondary">Ver detalles</a>
                    </div>
                    <div class="habitacion-card">
                        <!-- Reemplazar con imagen real: img/habitacion2.jpg -->
                        <img src="/img/deluxe1.jpg" alt="Habitación Deluxe">
                        <h3>Habitación Deluxe</h3>
                        <p>Amplio espacio con vistas espectaculares.</p>
                        <a href="index.jsp?do=user/habitacion&id=6" class="btn-secondary">Ver detalles</a>
                    </div>
                    <div class="habitacion-card">
                        <!-- Reemplazar con imagen real: img/habitacion3.jpg -->
                        <img src="/img/presidencial1.jpg" alt="Suite Presidencial">
                        <h3>Suite Presidencial</h3>
                        <p>El máximo lujo y exclusividad para tu estancia.</p>
                        <a href="index.jsp?do=user/habitacion&id=9" class="btn-secondary">Ver detalles</a>
                    </div>
                </div>
            </section>

            <!-- Sección de Servicios -->
            <section class="servicios">
                <h2>Nuestros Servicios</h2>
                <div class="servicios-grid">
                    <div class="servicio-card">
                        <i class="fas fa-utensils"></i>
                        <h3>Restaurante Gourmet</h3>
                        <p>Disfruta de nuestra exquisita gastronomía.</p>
                    </div>
                    <div class="servicio-card">
                        <i class="fas fa-spa"></i>
                        <h3>Spa & Bienestar</h3>
                        <p>Relájate con nuestros tratamientos exclusivos.</p>
                    </div>
                    <div class="servicio-card">
                        <i class="fas fa-swimming-pool"></i>
                        <h3>Piscina Infinity</h3>
                        <p>Vistas panorámicas desde nuestra piscina.</p>
                    </div>
                </div>
            </section>

            <!-- Footer -->
            <footer class="footer">
                <div class="footer-content">
                    <div class="footer-section">
                        <h3>Contacto</h3>
                        <p><i class="fas fa-map-marker-alt"></i> Av. Principal 123, Ciudad</p>
                        <p><i class="fas fa-phone"></i> +123 456 7890</p>
                        <p><i class="fas fa-envelope"></i> info@hotellisa.com</p>
                    </div>
                    <div class="footer-section">
                        <h3>Redes Sociales</h3>
                        <div class="social-icons">
                            <a href="#"><i class="fab fa-facebook"></i></a>
                            <a href="#"><i class="fab fa-instagram"></i></a>
                            <a href="#"><i class="fab fa-twitter"></i></a>
                        </div>
                    </div>
                </div>
                <div class="footer-bottom">
                    <p>&copy; 2025 Hotel Lisa. Todos los derechos reservados.</p>
                </div>
            </footer>

            <script src="js/script.js"></script>
        </body>
</html>