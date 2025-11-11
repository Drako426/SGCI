package com.sgci.repositorio;

import com.sgci.modelo.HistorialMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoRepositorio {

    // -----------------------------
    // GUARDAR NUEVO HISTORIAL
    // -----------------------------
    public void guardar(HistorialMedico h) throws Exception {
        String sql = "INSERT INTO historial_medico (id_paciente, id_medico, diagnostico, tratamiento, notas, estado_actual, creado_por, fecha_consulta, ultima_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, h.getIdPaciente());
            ps.setInt(2, h.getIdMedico());
            ps.setString(3, h.getDiagnostico());
            ps.setString(4, h.getTratamiento());
            ps.setString(5, h.getNotas());
            ps.setString(6, h.getEstadoActual());
            ps.setInt(7, h.getCreadoPor());
            ps.setTimestamp(8, new Timestamp(h.getFechaConsulta().getTime()));
            ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) h.setIdHistorial(rs.getInt(1));
            }
        }
    }

    // -----------------------------
    // BUSCAR POR CÉDULA DE PACIENTE
    // -----------------------------
    public List<HistorialMedico> buscarPorCedula(String cedula) throws SQLException {
        String sql = "SELECT hm.*, p.nombre AS paciente_nombre, p.cedula AS paciente_cedula " +
                     "FROM historial_medico hm JOIN paciente p ON hm.id_paciente = p.id_paciente " +
                     "WHERE p.cedula = ? ORDER BY hm.fecha_consulta DESC";
        List<HistorialMedico> lista = new ArrayList<>();
        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialMedico h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial"));
                    h.setIdPaciente(rs.getInt("id_paciente"));
                    h.setIdMedico(rs.getInt("id_medico"));
                    Timestamp fc = rs.getTimestamp("fecha_consulta");
                    if (fc != null) h.setFechaConsulta(new java.util.Date(fc.getTime()));
                    h.setDiagnostico(rs.getString("diagnostico"));
                    h.setTratamiento(rs.getString("tratamiento"));
                    h.setNotas(rs.getString("notas"));
                    h.setCreadoPor(rs.getInt("creado_por"));
                    Timestamp um = rs.getTimestamp("ultima_modificacion");
                    if (um != null) h.setUltimaModificacion(new java.util.Date(um.getTime()));

                    // Manejo seguro de null en estado_actual
                    String estado = rs.getString("estado_actual");
                    h.setEstadoActual((estado != null) ? estado : "BORRADOR");

                    h.setNombrePaciente(rs.getString("paciente_nombre"));
                    h.setCedulaPaciente(rs.getString("paciente_cedula"));
                    lista.add(h);
                }
            }
        }
        return lista;
    }

    // -----------------------------
    // ACTUALIZAR HISTORIAL EXISTENTE
    // -----------------------------
    public void actualizar(HistorialMedico h) throws Exception {
        String sql = "UPDATE historial_medico SET diagnostico=?, tratamiento=?, notas=?, estado_actual=?, ultima_modificacion=? WHERE id_historial=?";
        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, h.getDiagnostico());
            ps.setString(2, h.getTratamiento());
            ps.setString(3, h.getNotas());
            ps.setString(4, h.getEstadoActual());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, h.getIdHistorial());
            ps.executeUpdate();
        }
    }

    // -----------------------------
    // OBTENER HISTORIAL POR ID
    // -----------------------------
    public HistorialMedico obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM historial_medico WHERE id_historial = ?";
        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HistorialMedico h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial"));
                    h.setIdPaciente(rs.getInt("id_paciente"));
                    h.setIdMedico(rs.getInt("id_medico"));
                    Timestamp fc = rs.getTimestamp("fecha_consulta");
                    if (fc != null) h.setFechaConsulta(new java.util.Date(fc.getTime()));
                    h.setDiagnostico(rs.getString("diagnostico"));
                    h.setTratamiento(rs.getString("tratamiento"));
                    h.setNotas(rs.getString("notas"));
                    h.setCreadoPor(rs.getInt("creado_por"));
                    Timestamp um = rs.getTimestamp("ultima_modificacion");
                    if (um != null) h.setUltimaModificacion(new java.util.Date(um.getTime()));
                    h.setEstadoActual(rs.getString("estado_actual"));
                    return h;
                }
            }
        }
        return null;
    }
    
    // -----------------------------
    // LISTAR HISTORIALES POR ID DE PACIENTE
    // -----------------------------
    public List<HistorialMedico> listarPorPaciente(int idPaciente) throws Exception {
        String sql = "SELECT hm.*, p.nombre AS paciente_nombre, p.cedula AS paciente_cedula " +
                     "FROM historial_medico hm " +
                     "JOIN paciente p ON hm.id_paciente = p.id_paciente " +
                     "WHERE hm.id_paciente = ? ORDER BY hm.fecha_consulta DESC";
        List<HistorialMedico> lista = new ArrayList<>();

        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialMedico h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial"));
                    h.setIdPaciente(rs.getInt("id_paciente"));
                    h.setIdMedico(rs.getInt("id_medico"));

                    Timestamp fc = rs.getTimestamp("fecha_consulta");
                    if (fc != null) h.setFechaConsulta(new java.util.Date(fc.getTime()));

                    h.setDiagnostico(rs.getString("diagnostico"));
                    h.setTratamiento(rs.getString("tratamiento"));
                    h.setNotas(rs.getString("notas"));
                    h.setEstadoActual(rs.getString("estado_actual"));
                    h.setCreadoPor(rs.getInt("creado_por"));

                    Timestamp um = rs.getTimestamp("ultima_modificacion");
                    if (um != null) h.setUltimaModificacion(new java.util.Date(um.getTime()));

                    h.setNombrePaciente(rs.getString("paciente_nombre"));
                    h.setCedulaPaciente(rs.getString("paciente_cedula"));

                    lista.add(h);
                }
            }
        }
        return lista;
    }
}