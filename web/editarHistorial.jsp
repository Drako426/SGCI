<%-- 
    Document   : editarHistorial
    Created on : 24/10/2025, 2:45:27 p. m.
    Author     : Juan Pablo
--%>

<%@ page import="com.sgci.modelo.HistorialMedico" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HistorialMedico h = (HistorialMedico) request.getAttribute("historial");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Historial Médico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<header>SGCI - Editar Historial</header>

<div class="container">
    <h2>Editar Historial ID: <%= h.getIdHistorial() %></h2>

    <form method="post" action="${pageContext.request.contextPath}/historial/editar">
        <input type="hidden" name="idHistorial" value="<%= h.getIdHistorial() %>">

        <label>ID Paciente:</label>
        <input type="number" name="idPaciente" value="<%= h.getIdPaciente() %>" readonly>

        <label>Fecha Consulta:</label>
        <input type="text" value="<%= h.getFechaConsulta() %>" readonly>

        <label>Diagnóstico:</label>
        <textarea name="diagnostico" rows="3"><%= h.getDiagnostico() %></textarea>

        <label>Tratamiento:</label>
        <textarea name="tratamiento" rows="3"><%= h.getTratamiento() %></textarea>

        <label>Notas:</label>
        <textarea name="notas" rows="3"><%= h.getNotas() %></textarea>

        <label>Estado:</label>
        <select name="estadoActual">
            <option value="BORRADOR" <%= "BORRADOR".equals(h.getEstadoActual()) ? "selected" : "" %>>Borrador</option>
            <option value="EN_REVISION" <%= "EN_REVISION".equals(h.getEstadoActual()) ? "selected" : "" %>>En revisión</option>
            <option value="APROBADO" <%= "APROBADO".equals(h.getEstadoActual()) ? "selected" : "" %>>Aprobado</option>
            <option value="ARCHIVADO" <%= "ARCHIVADO".equals(h.getEstadoActual()) ? "selected" : "" %>>Archivado</option>
        </select>


        <button type="submit">Guardar Cambios</button>
    </form>
</div>

<footer>© 2025 SGCI - Grupo 6</footer>
</body>
</html>