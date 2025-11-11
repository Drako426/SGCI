<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Historial Médico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script>
        function buscarPaciente() {
            const cedula = document.getElementById("cedula").value.trim();
            if (!cedula) return;

            fetch(window.location.origin + "${pageContext.request.contextPath}/buscarPaciente?cedula=" + cedula)
                .then(res => {
                    if (!res.ok) throw new Error("Error HTTP: " + res.status);
                    return res.json();
                })
                .then(data => {
                    if (data && data.nombre) {
                        document.getElementById("nombrePaciente").value = data.nombre;
                    } else {
                        alert("Paciente no encontrado");
                        document.getElementById("nombrePaciente").value = "";
                    }
                })
                .catch(err => alert("Error al buscar el paciente: " + err.message));
        }
        
        function buscarMedico() {
            const nombre = document.getElementById("nombreMedico").value;
            if (nombre.trim() !== "") {
                fetch("${pageContext.request.contextPath}/buscarMedico?nombre=" + nombre)
                    .then(res => res.json())
                    .then(data => {
                        if (data.especialidad) {
                            document.getElementById("especialidadMedico").value = data.especialidad;
                        } else {
                            alert("Médico no encontrado");
                        }
                    })
                    .catch(() => alert("Error al buscar el médico"));
            }
        }
    </script>
</head>
<body>
<header>SGCI - Gestión de Historiales Médicos</header>

<div class="container">
    <h2>Crear Nuevo Historial</h2>

    <form method="post" action="${pageContext.request.contextPath}/historial/crear">
        <label>Cédula del Paciente:</label>
        <input type="text" id="cedula" name="cedula" onblur="buscarPaciente()" required>

        <label>Nombre del Paciente:</label>
        <input type="text" id="nombrePaciente" name="nombrePaciente" readonly required>

        <label>Nombre del Médico:</label>
        <input type="text" id="nombreMedico" name="nombreMedico" onblur="buscarMedico()" required>

        <label>Especialidad:</label>
        <input type="text" id="especialidadMedico" name="especialidadMedico" readonly>

        <label>Fecha de Consulta:</label>
        <input type="datetime-local" name="fechaConsulta" required>

        <label>Diagnóstico:</label>
        <textarea name="diagnostico" rows="3" required></textarea>

        <label>Tratamiento:</label>
        <textarea name="tratamiento" rows="3" required></textarea>

        <label>Notas:</label>
        <textarea name="notas" rows="3"></textarea>

        <label>Estado inicial:</label>
        <select name="estadoActual" required>
            <option value="BORRADOR">Borrador</option>
            <option value="EN_REVISION">En revisión</option>
            <option value="APROBADO">Aprobado</option>
            <option value="ARCHIVADO">Archivado</option>
        </select>

        <button type="submit">Guardar Historial</button>
    </form>
</div>

<footer>© 2025 SGCI - Grupo 6</footer>
</body>
</html>