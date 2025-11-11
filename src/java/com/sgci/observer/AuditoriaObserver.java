/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.observer;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;
import com.sgci.repositorio.DBConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class AuditoriaObserver implements HistorialObserver {

    @Override
    public void actualizar(String evento, HistorialMedico h) {
        String sql = "INSERT INTO log_auditoria (id_historial, id_usuario, accion, fecha, cambios) VALUES (?,?,?,?,?)";
        try (Connection c = DBConexion.getInstancia().getConexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, h.getIdHistorial());
            ps.setInt(2, h.getCreadoPor()); // puede ajustarse
            ps.setString(3, evento);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, "Historial " + evento + " por usuario id=" + h.getCreadoPor());
            ps.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error auditoria: " + ex.getMessage());
        }
    }
}