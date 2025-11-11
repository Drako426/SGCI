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

public class EnRevisionState implements EstadoHistorial {

    @Override
    public void avanzarEstado(HistorialMedico historial) {
        historial.setEstado(new AprobadoState());
    }

    @Override
    public void retrocederEstado(HistorialMedico historial) {
        historial.setEstado(new BorradorState());
    }

    @Override
    public String getNombre() {
        return "EN_REVISION";
    }
}