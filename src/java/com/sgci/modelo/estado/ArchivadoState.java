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

public class ArchivadoState implements EstadoHistorial {

    @Override
    public void avanzarEstado(HistorialMedico historial) {
        System.out.println("El historial ya está archivado. No puede avanzar más.");
    }

    @Override
    public void retrocederEstado(HistorialMedico historial) {
        historial.setEstado(new AprobadoState());
    }

    @Override
    public String getNombre() {
        return "ARCHIVADO";
    }
}