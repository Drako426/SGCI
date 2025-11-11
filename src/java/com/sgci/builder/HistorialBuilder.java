/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.builder;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;
import java.util.Date;

public class HistorialBuilder {
    private final HistorialMedico h;

    public HistorialBuilder() {
        h = new HistorialMedico();
    }

    public HistorialBuilder conPaciente(int idPaciente) {
        h.setIdPaciente(idPaciente);
        return this;
    }
    public HistorialBuilder conMedico(int idMedico) {
        h.setIdMedico(idMedico);
        return this;
    }
    public HistorialBuilder conFecha(Date fecha) {
        h.setFechaConsulta(fecha);
        return this;
    }
    public HistorialBuilder conDiagnostico(String diagnostico) {
        h.setDiagnostico(diagnostico);
        return this;
    }
    public HistorialBuilder conTratamiento(String tratamiento) {
        h.setTratamiento(tratamiento);
        return this;
    }
    public HistorialBuilder conNotas(String notas) {
        h.setNotas(notas);
        return this;
    }
    public HistorialBuilder creadoPor(int idUsuario) {
        h.setCreadoPor(idUsuario);
        return this;
    }
    public HistorialBuilder conEstadoActual(String estado) {
        h.setEstadoActual(estado);
        return this;
    }

    public HistorialMedico build() {
        return h;
    }
}