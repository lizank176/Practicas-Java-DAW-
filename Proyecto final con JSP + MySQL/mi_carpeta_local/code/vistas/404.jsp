<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>404 - Página no encontrada | Hotel Lisa</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #6a0dad, #b266ff);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #fff;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            text-align: center;
        }

        .error-container {
            max-width: 600px;
            padding: 40px;
            background-color: rgba(255, 255, 255, 0.05);
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.3);
        }

        .error-code {
            font-size: 120px;
            font-weight: bold;
            margin: 0;
            color: #ffffffdd;
            text-shadow: 0 0 15px #cfa0ff;
        }

        .error-message {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .btn-home {
            display: inline-block;
            padding: 12px 25px;
            margin-top: 20px;
            background-color: #9c27b0;
            color: white;
            border: none;
            border-radius: 30px;
            font-size: 16px;
            text-decoration: none;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #7b1fa2;
        }

        .hotel-icon {
            font-size: 50px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="hotel-icon">🏨</div>
        <div class="error-code">404</div>
        <div class="error-message">Lo sentimos, no hemos encontrado esta habitación... 🛏️</div>
        <p>Parece que esta página no existe o ha sido remodelada recientemente.</p>
        <a href="index.jsp?do=user/principal" class="btn-home">Volver a la recepción</a>
    </div>
</body>
</html>
