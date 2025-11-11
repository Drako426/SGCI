/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.modelo;

/**
 *
 * @author Juan Pablo
 */
import java.util.Date;

public class Antecedentes {
    private int idAntecedente;
    private int idPaciente;
    private int idMedico;
    private String tipoAntecedente;
    private String descripcion;
    private Date fechaRegistro;
    private boolean esCritico;

    public int getIdAntecedente() { return idAntecedente; }
    public void setIdAntecedente(int idAntecedente) { this.idAntecedente = idAntecedente; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public String getTipoAntecedente() { return tipoAntecedente; }
    public void setTipoAntecedente(String tipoAntecedente) { this.tipoAntecedente = tipoAntecedente; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public boolean isEsCritico() { return esCritico; }
    public void setEsCritico(boolean esCritico) { this.esCritico = esCritico; }
}