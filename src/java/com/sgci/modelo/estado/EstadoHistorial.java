/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.modelo.estado;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;

public interface EstadoHistorial {
    void avanzarEstado(HistorialMedico historial);
    void retrocederEstado(HistorialMedico historial);
    String getNombre();
}