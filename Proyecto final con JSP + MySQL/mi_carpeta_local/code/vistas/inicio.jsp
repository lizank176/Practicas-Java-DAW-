<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
    <img src="/img/hotel-lisa.png" alt="logo">
    <form method="post" action="index.jsp?do=user/login" class="login-form">
        <div class="login">
            <div class="login-screen">
                <div class="app-title">
                    <h1>Welcome</h1>
                </div>
                <input type="text" class="login-field" name="username" placeholder="Username" required>
                <input type="password" class="login-field" name="password" placeholder="Password" required>
                <button type="submit" class="btn btn-primary btn-large btn-block">Login</button>
                
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message" style="color: red; margin-top: 10px; text-align: center;">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                 <a class="login-link" href="index.jsp?do=user/registrarse">Registrarse</a>
            </div>
        </div>
       
    </form>
    
</body>
</html>