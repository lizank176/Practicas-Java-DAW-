<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro - Hotel Lisa</title>
  <link rel="stylesheet" href="/css/login.css">
</head>
<body>
  <img src="/img/hotel-lisa.png" alt="Logo Hotel Lisa" class="logo">

  <div class="login">
    <div class="login-screen">
      <div class="app-title">
        <h1>Registrarse</h1>
      </div>

      <% if (request.getAttribute("mensaje") != null) { %>
        <p style="color:green;"><%= request.getAttribute("mensaje") %></p>
      <% } %>
      
      <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
      <% } %>

      <form class="login-form" method="post">
        <div class="control-group">
          <input type="text" class="login-field" name="nombre" placeholder="Nombre" required>
        </div>

        <div class="control-group">
          <input type="text" class="login-field" name="apellidos" placeholder="Apellidos" required>
        </div>

        <div class="control-group">
          <input type="text" class="login-field" name="username" placeholder="Nombre de usuario" required>
        </div>

        <div class="control-group">
          <input type="password" class="login-field" name="password" placeholder="Contraseña" required>
        </div>
        <div class="control-group">
          <input type="email" class="login-field" name="email" placeholder="Email" required>
        </div>
        <div class="control-group">
          <input type="text" class="login-field" name="telefono" placeholder="Teléfono" required>
        </div>
        <button type="submit" class="btn btn-primary btn-large btn-block">Registrarse</button>

        <a class="login-link" href="index.jsp?do=user/inicio">¿Ya tienes cuenta? Inicia sesión</a>
      </form>
    </div>
  </div>
</body>
</html>