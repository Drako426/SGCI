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

public class NotificacionObserver implements HistorialObserver {

    @Override
    public void actualizar(String evento, HistorialMedico h) {
        System.out.println("Notificación: historial " + evento + " para paciente ID=" + h.getIdPaciente());
    }
}