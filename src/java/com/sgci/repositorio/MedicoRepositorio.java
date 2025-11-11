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
import com.sgci.modelo.Medico;

public class MedicoRepositorio {

    // Buscar médico por nombre (exact match)
    public Medico buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isEmpty()) return null;

        String sql = "SELECT id_medico, nombre, especialidad, correo FROM medico WHERE nombre = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico m = new Medico();
                    // Usuario tiene setId, setNombre, setCorreo (heredados)
                    m.setId(rs.getInt("id_medico"));
                    m.setNombre(rs.getString("nombre"));
                    m.setEspecialidad(rs.getString("especialidad"));
                    m.setCorreo(rs.getString("correo"));
                    return m;
                }
            }
        }
        return null;
    }

    // Obtener id por nombre (simple)
    public int obtenerIdPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_medico FROM medico WHERE nombre = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_medico");
            }
        }
        return -1;
    }

    // Obtener nombre por id
    public String obtenerNombrePorId(int id) throws SQLException {
        String sql = "SELECT nombre FROM medico WHERE id_medico = ?";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("nombre");
            }
        }
        return "Desconocido";
    }

    // Crear médico (inserta nombre y opcionalmente especialidad)
    public int crearMedico(String nombre, String especialidad) throws SQLException {
        if (nombre == null || nombre.isEmpty()) throw new SQLException("Nombre de médico requerido");

        String sql = "INSERT INTO medico (nombre, especialidad) VALUES (?, ?)";
        try (Connection conn = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            if (especialidad != null && !especialidad.isEmpty()) ps.setString(2, especialidad);
            else ps.setNull(2, Types.VARCHAR);

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    // Variante simple si solo quieres crear con nombre (sin especialidad)
    public int crearMedico(String nombre) throws SQLException {
        return crearMedico(nombre, null);
    }
}