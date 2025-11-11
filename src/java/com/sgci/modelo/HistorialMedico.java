/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.modelo;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.estado.*;
import java.util.Date;

public class HistorialMedico {

    private int idHistorial;
    private int idPaciente;
    private int idMedico;
    private Integer idCita;
    private Date fechaConsulta;
    private String diagnostico;
    private String tratamiento;
    private String notas;
    private int creadoPor;
    private Date ultimaModificacion;
    private EstadoHistorial estado;
    private String estadoActual;
    private boolean esBorrador;

    // Campos auxiliares (no persistidos directamente en historiales_medicos)
    private String nombrePaciente;
    private String cedulaPaciente;

    // --- Constructor ---
    public HistorialMedico() {
        this.estado = new BorradorState();
        this.estadoActual = estado.getNombre();
    }

    // --- Métodos de transición ---
    public void avanzarEstado() {
        estado.avanzarEstado(this);
        this.estadoActual = estado.getNombre();
    }

    public void retrocederEstado() {
        estado.retrocederEstado(this);
        this.estadoActual = estado.getNombre();
    }

    // --- Getters y Setters ---
    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public Date getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(Date fechaConsulta) { this.fechaConsulta = fechaConsulta; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public int getCreadoPor() { return creadoPor; }
    public void setCreadoPor(int creadoPor) { this.creadoPor = creadoPor; }

    public Date getUltimaModificacion() { return ultimaModificacion; }
    public void setUltimaModificacion(Date ultimaModificacion) { this.ultimaModificacion = ultimaModificacion; }

    public boolean isEsBorrador() { return esBorrador; }
    public void setEsBorrador(boolean esBorrador) { this.esBorrador = esBorrador; }

    // --- Métodos del patrón State ---
    public EstadoHistorial getEstado() { return estado; }
    public void setEstado(EstadoHistorial estado) { 
        this.estado = estado; 
        this.estadoActual = estado.getNombre();
    }

    public String getEstadoActual() { return estadoActual; }
    public void setEstadoActual(String estadoActual) { 
        this.estadoActual = estadoActual; 
        switch (estadoActual) {
            case "EN_REVISION": this.estado = new EnRevisionState(); break;
            case "APROBADO": this.estado = new AprobadoState(); break;
            case "ARCHIVADO": this.estado = new ArchivadoState(); break;
            default: this.estado = new BorradorState(); break;
        }
    }

    // --- Getters/Setters auxiliares (para UI / sesión) ---
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getCedulaPaciente() { return cedulaPaciente; }
    public void setCedulaPaciente(String cedulaPaciente) { this.cedulaPaciente = cedulaPaciente; }
}
