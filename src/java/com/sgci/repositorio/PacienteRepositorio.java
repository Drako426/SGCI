/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.repositorio;

/**
 *
 * @author Juan Pablo
 */
import java.sql.*;
import com.sgci.modelo.Paciente;

public class PacienteRepositorio {

    // Buscar paciente por cédula
    public Paciente buscarPorCedula(String cedula) throws SQLException {
        if (cedula == null || cedula.isEmpty()) return null;

        String sql = "SELECT id_paciente, nombre, cedula, fecha_nacimiento FROM paciente WHERE cedula = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id_paciente"));
                p.setNombre(rs.getString("nombre"));
                p.setNumeroIdentificacion(rs.getString("cedula"));
                Date fn = rs.getDate("fecha_nacimiento");
                if (fn != null) p.setFechaNacimiento(new java.sql.Date(fn.getTime()));
                return p;
            }
        }
        return null;
    }

    // Crear paciente
    public int crearPaciente(String nombre, String cedula) throws SQLException {
        if (nombre == null || cedula == null) throw new SQLException("Nombre o cédula nulos");

        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO paciente (nombre, cedula) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, cedula);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    // Obtener ID por nombre (si lo necesitas en otros servlets)
    public int obtenerIdPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_paciente FROM paciente WHERE nombre = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_paciente");
        }
        return -1;
    }

    // Obtener nombre por ID (si lo necesitas en historial)
    public String obtenerNombrePorId(int idPaciente) throws SQLException {
        String sql = "SELECT nombre FROM paciente WHERE id_paciente = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("nombre");
        }
        return "Desconocido";
    }
}