<%@ page import="java.util.List" %> 
<%@ page import="com.sgci.modelo.HistorialMedico" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historiales del Paciente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<header>SGCI - Historiales Médicos</header>

<div class="container">
    <h2>Buscar Antecedentes</h2>

    <form method="get" action="${pageContext.request.contextPath}/historial/listar">
        <input type="text" name="busqueda" placeholder="Buscar por cédula o nombre" required>
        <button type="submit" class="btn">Buscar</button>
    </form>

    <a href="${pageContext.request.contextPath}/historial/crear" class="btn">+ Nuevo Historial</a>

    <%
        List<HistorialMedico> lista = (List<HistorialMedico>) request.getAttribute("lista");
        if (lista != null && !lista.isEmpty()) {
    %>
    <table>
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Paciente</th>
            <th>Diagnóstico</th>
            <th>Tratamiento</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <% for (HistorialMedico h : lista) { %>
            <tr>
                <td><%= h.getIdHistorial() %></td>
                <td><%= h.getFechaConsulta() %></td>
                <td><%= h.getNombrePaciente() %></td>
                <td>
                    <%= (h.getDiagnostico() != null && h.getDiagnostico().length() > 50) 
                        ? h.getDiagnostico().substring(0, 50) + "..." 
                        : h.getDiagnostico() %>
                </td>
                <td>
                    <%= (h.getTratamiento() != null && h.getTratamiento().length() > 50) 
                        ? h.getTratamiento().substring(0, 50) + "..." 
                        : h.getTratamiento() %>
                </td>
                <td><%= h.getEstadoActual() %></td>
                <td>
                    <a class="btn" href="<%= request.getContextPath() + "/historial/editar?id=" + h.getIdHistorial() %>">Editar</a>
                    <a class="btn btn-danger" href="<%= request.getContextPath() + "/historial/descargar?file=historial_" + h.getIdHistorial() + ".pdf" %>">PDF</a>
                </td>
            </tr>
        <% } %>
    </table>
    <% } else { %>
        <p>No hay historiales encontrados.</p>
    <% } %>
</div>

<footer>© 2025 SGCI - Grupo 6</footer>
</body>
</html>
