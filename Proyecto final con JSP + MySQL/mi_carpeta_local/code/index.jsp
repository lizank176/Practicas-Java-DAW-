<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.UsuarioDAO" %>
<%@ page import="model.HabitacionDAO" %>
<%@ page import="model.Habitacion" %>
<%@ page import="model.ReservaDAO" %>
<%@ page import="model.Reserva" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>

<%
    // Obtener el parámetro 'do' para controlar la acción a realizar en la página

    String doParam = request.getParameter("do");
    // Si no se especifica ninguna acción, se asigna la vista de inicio por defecto
    if (doParam == null) {
        doParam = "user/inicio";
    }
    // Estructura switch para manejar diferentes acciones según el valor de 'do'
    switch (doParam) {
        // Acción para mostrar la página de inicio 
        case "user/inicio":
            %><jsp:include page="vistas/inicio.jsp" /><%
            break;
        // Acción para procesar el login de usuario    
        case "user/login":
            // Obtener la sesión actual
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // Comprobar que ambos campos han sido enviados
            if (username != null && password != null) {
                UsuarioDAO dao = new UsuarioDAO();
                // Obtener el rol del usuario si las credenciales son válidas
                String rol = dao.obtenerRolUsuario(username, password);
                
                if (rol != null) {
                    // Guardar datos del usuario en la sesión y redirigir a la página principal
                    session.setAttribute("usuario", username);
                    session.setAttribute("rol", rol);
                    response.sendRedirect("index.jsp?do=user/principal");
                    return;
                } else {
                    request.setAttribute("error", "Credenciales incorrectas");
                    %><jsp:include page="vistas/inicio.jsp" /><%
                }
            }
            break;
        // Acción para mostrar la página principal del usuario logueado    
        case "user/principal":
            // Verificar si hay un usuario en sesión; si no, redirigir a inicio
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }
            %><jsp:include page="vistas/principal.jsp" /><%
            break;
        // Acción para registrar un nuevo usuario    
        case "user/registrarse":
            // Procesar registro si es POST
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String usernameReg = request.getParameter("username");
                String passwordReg = request.getParameter("password");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");

                // Validación básica
                if (nombre == null || nombre.isEmpty() || 
                    apellidos == null || apellidos.isEmpty() ||
                    usernameReg == null || usernameReg.isEmpty() || 
                    passwordReg == null || passwordReg.isEmpty() ||
                    email == null || email.isEmpty() ||
                    telefono == null || telefono.isEmpty()) {
                    request.setAttribute("error", "Todos los campos son obligatorios");
                } else {
                    UsuarioDAO dao = new UsuarioDAO();
                    try {
                        // Verificar si el usuario ya existe
                        if (dao.existeUsuario(usernameReg)) {
                            request.setAttribute("error", "El nombre de usuario ya está registrado.");
                        } else {
                            // Asignar rol por defecto (por ejemplo "cliente")
                            String rol = "cliente";
                            dao.registrarUsuario(nombre, apellidos, usernameReg, passwordReg, email, telefono, rol);
                            session.setAttribute("usuario", usernameReg);
                            session.setAttribute("rol", rol);
                            request.setAttribute("mensaje", "Registro exitoso! Redirigiendo...");
                            
                            // Redirigir después de 2 segundos
                            response.setHeader("Refresh", "2;URL=index.jsp?do=user/principal");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", "Error en el registro: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            %><jsp:include page="vistas/registrarse.jsp" /><%
            break;

        // Acción para listar habitaciones disponibles
        case "user/habitaciones":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }
            // Obtener lista de habitaciones desde la base de datos
            HabitacionDAO dao = new HabitacionDAO();
            List<Habitacion> habitaciones = dao.listarHabitaciones();
            request.setAttribute("habitaciones", habitaciones);
                
            %><jsp:include page="vistas/habitaciones.jsp" /><%
            break;
        // Acción para mostrar detalles de una habitación específica
        case "user/habitacion":
                if (session.getAttribute("usuario") == null) {
                    response.sendRedirect("index.jsp?do=user/inicio");
                    return;
                }
                // Obtener el parámetro id de la habitación
                String idHabitacionStr = request.getParameter("id");
                if (idHabitacionStr == null || idHabitacionStr.isEmpty()) {
                    response.sendRedirect("index.jsp?do=user/habitaciones&error=faltan_parametros");
                    return;
                }
                // Obtener la habitación de la base de datos
                int idHabitacion = Integer.parseInt(idHabitacionStr);
                HabitacionDAO habitacion = new HabitacionDAO();
                Habitacion habitacion2 = habitacion.obtenerHabitacionPorId(idHabitacion);
                request.setAttribute("habitacion", habitacion2);

                %><jsp:include page="vistas/habitacion.jsp" /><%
                break;
        // Acción para gestionar reservas, tanto para admin como para cliente
        case "user/reservas":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            } else {
                // Usa la variable usuarioReserva que ya estaba declarada anteriormente
                String usuarioReserva = (String) session.getAttribute("usuario");
                String rol = (String) session.getAttribute("rol");
                ReservaDAO reservaDAO = new ReservaDAO();
                UsuarioDAO usuarioDAO = new UsuarioDAO();

                // Parámetro para filtrado
                String filtroEstado = request.getParameter("filtro");
                
                if ("admin".equals(rol)) {
                    // Lógica para administrador
                    List<Reserva> reservas;
                    
                    if ("confirmadas".equals(filtroEstado)) {
                        reservas = reservaDAO.getReservasPorEstado("Confirmada");
                    } else if ("pendientes".equals(filtroEstado)) {
                        reservas = reservaDAO.getReservasPorEstado("Pendiente");
                    } else {
                        // Todas las reservas para admin
                        reservas = reservaDAO.getReservasAdmin();
                    }
                    
                    request.setAttribute("reservas", reservas);
                    %><jsp:include page="vistas/reservas_admin.jsp" /><%
                } else {
                    // Lógica para cliente normal
                    int idUsuario = usuarioDAO.getIdPorUsername(usuarioReserva);
                    List<Reserva> reservas;
                    
                    if ("confirmadas".equals(filtroEstado)) {
                        reservas = reservaDAO.getReservasConfirmadasPorUsuario(idUsuario);
                    } else if ("pendientes".equals(filtroEstado)) {
                        reservas = reservaDAO.getReservasPendientesPorUsuario(idUsuario);
                    } else {
                        // Todas las reservas del cliente
                        reservas = reservaDAO.getReservasPorUsuario(idUsuario);
                    }
                    
                    request.setAttribute("reservas", reservas);
                    %><jsp:include page="vistas/reservas_cliente.jsp" /><%
                }
            }
            break;
        // Acción para guardar una nueva reserva
        case "user/guardarReserva":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }
            // Obtener el nombre de usuario del usuario que está haciendo la reserva
            String usernameReserva = (String) session.getAttribute("usuario");

            UsuarioDAO usuarioDAOReserva = new UsuarioDAO();
            // Obtener el ID del usuario a partir del nombre de usuario
            int idUsuarioReserva = usuarioDAOReserva.getIdPorUsername(usernameReserva);
            // Obtener parámetros de formulario de reserva
            int idHabitacionReserva = Integer.parseInt(request.getParameter("idHabitacion"));
            Date fechaEntrada = Date.valueOf(request.getParameter("fechaEntrada"));
            Date fechaSalida = Date.valueOf(request.getParameter("fechaSalida"));


            HabitacionDAO habitacionDAO = new HabitacionDAO();
            Habitacion habitacionReserva = habitacionDAO.obtenerHabitacionPorId(idHabitacionReserva);
            // Calcular el total de días entre fechas
            long dias = (fechaSalida.getTime() - fechaEntrada.getTime()) / (1000 * 60 * 60 * 24);
            if (dias < 1) dias = 1; // Mínimo una noche
            // Calcular el precio total de la reserva
            double precioTotal = dias * habitacionReserva.getPrecioNoche();
            // Crear un nuevo objeto Reserva con los datos obtenidos
            Reserva reserva_new = new Reserva();
            Usuario cliente = new Usuario();
            cliente.setId(idUsuarioReserva);
            reserva_new.setCliente(cliente);
            reserva_new.setHabitacion(habitacionReserva);
            reserva_new.setFechaEntrada(fechaEntrada);
            reserva_new.setFechaSalida(fechaSalida);
            reserva_new.setPrecioTotal(precioTotal);
            reserva_new.setAnticipo(0);
            reserva_new.setEstado("Pendiente");
            reserva_new.setObservaciones("");
            // Crear nueva reserva
            ReservaDAO reservaDAO = new ReservaDAO();
            int resultado = reservaDAO.insertarReserva(reserva_new);

            if (resultado > 0) {
                response.sendRedirect("index.jsp?do=user/reservas");
            } else {
                response.sendRedirect("index.jsp?do=user/habitacion&id=" + idHabitacionReserva + "&error=1");
            }
            
            %><jsp:include page="vistas/reservas_cliente.jsp" /><%

            break;
        // Acción para eliminar una reserva
        case "user/eliminarReserva":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }
            // Obtener el ID de la reserva a eliminar
            int idReserva = Integer.parseInt(request.getParameter("idReserva"));
            ReservaDAO reservaDAOEliminar = new ReservaDAO();
            boolean resultadoEliminar = reservaDAOEliminar.eliminarReserva(idReserva);
            // Redirigir según el resultado de la eliminación
            if (resultadoEliminar) {
                response.sendRedirect("index.jsp?do=user/reservas");
            } else {
                response.sendRedirect("index.jsp?do=user/reservas&error=1");
            }
            break;
        // Acción para pagar una reserva
        case "user/pagar":
            if (session.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp?do=user/inicio");
            return;
            }
            // Obtener el ID de la reserva a pagar desde los parámetros de la solicitud
            String idParam = request.getParameter("idReserva");

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("index.jsp?do=user/reservas&error=missing_id");
                return;
            }
            // Convertir el ID de la reserva a entero
            int idReservaPagar = Integer.parseInt(idParam);
            // Obtener la reserva correspondiente desde la base de datos
            ReservaDAO reservaDAOPagar = new ReservaDAO();
            Reserva reservaPagar = reservaDAOPagar.getReservaPorId(idReservaPagar);

            if (reservaPagar == null) {
                response.sendRedirect("index.jsp?do=user/reservas&error=1");
                return;
            }

            String accion = request.getParameter("accion");

            if ("confirmar".equals(accion)) {
                // El usuario ha enviado el formulario de pago (confirmación final)
                reservaPagar.setEstado("Confirmada");
                reservaDAOPagar.actualizarReserva(reservaPagar);
                response.sendRedirect("index.jsp?do=user/reservas&msg=pagado");
            } else {
                // El usuario acaba de pulsar "Pagar", mostrar la página de pago
                request.setAttribute("reserva", reservaPagar);
                RequestDispatcher rd = request.getRequestDispatcher("vistas/pago.jsp");
                rd.forward(request, response); // aquí se muestra la vista
            }

            break;
        // Acción para editar una reserva existente
        case "user/editarReserva":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }
            // Obtener el ID de la reserva a editar desde los parámetros de la solicitud
            String idReservaEditarStr = request.getParameter("idReserva");
            if (idReservaEditarStr == null || idReservaEditarStr.isEmpty()) {
                response.sendRedirect("index.jsp?do=user/reservas&error=param");
                return;
            }
            // Convertir el ID de la reserva a entero
            int idReservaEditar = Integer.parseInt(idReservaEditarStr);
            ReservaDAO reservaDAOEditar = new ReservaDAO();
            Reserva reservaEditar = reservaDAOEditar.getReservaPorId(idReservaEditar);
            if (reservaEditar != null) {
                request.setAttribute("reserva", reservaEditar);
                request.getRequestDispatcher("vistas/editarReserva.jsp").forward(request, response);
            } else {
                response.sendRedirect("index.jsp?do=user/reservas&error=1");
            }

            break;
        // Acción para confirmar el pago de una reserva
        case "user/confirmarPago":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }

            try {
                // Recoger los datos del formulario de pago
                int reservaId = Integer.parseInt(request.getParameter("reservaId"));
                String nombreTitular = request.getParameter("nombreTitular");
                String numeroTarjeta = request.getParameter("numeroTarjeta");
                String fechaExpiracion = request.getParameter("fechaExpiracion");
                String cvv = request.getParameter("cvv");

                // Validación básica de campos (puedes mejorarla)
                if (nombreTitular == null || numeroTarjeta == null || fechaExpiracion == null || cvv == null) {
                    response.sendRedirect("index.jsp?do=user/reservas&error=datos_pago");
                    return;
                }

               
                // Actualizar estado de la reserva
                ReservaDAO reservaDAO2 = new ReservaDAO();
                // Supongamos que esta es tu función de pago

                boolean pagoExitoso = reservaDAO2.actualizarEstadoReserva(reservaId, "Confirmada");

                if (pagoExitoso) {
                    // Redirige al usuario a sus reservas con mensaje de éxito
                    response.sendRedirect("index.jsp?do=user/reservas&mensaje=pago_ok");
                } else {
                    response.sendRedirect("index.jsp?do=user/reservas&error=pago_fallo");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp?do=user/reservas&error=excepcion_pago");
            }
            break;
        // Acción para pagar más tarde una reserva
        case "user/pagarMasTarde":
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp?do=user/inicio");
                return;
            }

            try {
                // Obtener el ID de la reserva desde los parámetros de la solicitud
                int reservaId = Integer.parseInt(request.getParameter("idReserva"));
                ReservaDAO reserva_estado = new ReservaDAO();
                
                // Actualizar estado de la reserva a "Pendiente"
                boolean actualizacionExitosa = reserva_estado.actualizarEstadoReserva(reservaId, "Pendiente");
                
                if (actualizacionExitosa) {
                    response.sendRedirect("index.jsp?do=user/reservas&mensaje=reserva_pendiente");
                } else {
                    response.sendRedirect("index.jsp?do=user/reservas&error=actualizacion_estado");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp?do=user/reservas&error=excepcion_actualizacion");
            }
            break;

        case "user/guardarCambios":  // ********** GUARDAR CAMBIOS EN RESERVA *******
            // Recoger datos del formulario
            int idReservaCambios = Integer.parseInt(request.getParameter("idReserva"));
            int idHabitacionCambios = Integer.parseInt(request.getParameter("idHabitacion"));
            String fechaEntradaCambios = request.getParameter("fechaEntrada");
            String fechaSalidaCambios = request.getParameter("fechaSalida");
            double precioTotalCambios = Double.parseDouble(request.getParameter("precioTotal"));
            int numHuespedes2 = Integer.parseInt(request.getParameter("numHuespedes"));
            String estado2 = request.getParameter("estado");
        
            // Opcional: cargar la habitación si tu constructor de Reserva la necesita
            Habitacion habitacionCambios = new Habitacion();
            habitacionCambios.setIdHabitacion(idHabitacionCambios);
            // Podrías cargarla de la BD si necesitas más datos
            Date fechaEntrada2 = Date.valueOf(fechaEntradaCambios);
            Date fechaSalida2 = Date.valueOf(fechaSalidaCambios);
            // Crear un objeto Reserva con los datos actualizados
            Reserva reservaActualizada = new Reserva();
            reservaActualizada.setIdReserva(idReservaCambios);
            reservaActualizada.setHabitacion(habitacionCambios);
            reservaActualizada.setFechaEntrada(fechaEntrada2);
            reservaActualizada.setFechaSalida(fechaSalida2);
            reservaActualizada.setPrecioTotal(precioTotalCambios);
            reservaActualizada.setHuespedes(numHuespedes2);
            reservaActualizada.setEstado(estado2);
            ReservaDAO reservaDAOCambios = new ReservaDAO();

            reservaDAOCambios.guardarCambios(reservaActualizada);
            // Redirigir a la vista de reservas (puede ser lista del usuario o admin)
            response.sendRedirect("index.jsp?do=user/reservas");
            break;
        // Acción para buscar reservas por término
        case "user/buscar":
            try {
                // 1. Obtener parámetro de búsqueda (si existe)
                String terminoBusqueda = request.getParameter("buscar");
                
                // 2. Obtener lista de reservas según búsqueda
                ReservaDAO reservaDAOBuscar = new ReservaDAO();
                List<Reserva> reservas;
                
                if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
                    // Buscar reservas por término (usando el método existente o uno nuevo)
                    reservas = reservaDAOBuscar.buscarReservas(terminoBusqueda); 
                } else {
                    // Si no hay búsqueda, obtener todas las reservas
                    reservas = reservaDAOBuscar.getReservasAdmin();
                }
                
                // 3. Pasar resultados al JSP
                request.setAttribute("reservas", reservas);
                request.getRequestDispatcher("vistas/reservas_admin.jsp").forward(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp?do=user/reservas&error=Error+al+buscar");
            }
            break; 
        default:
            %><jsp:include page="vistas/404.jsp" /><%
        }
%>