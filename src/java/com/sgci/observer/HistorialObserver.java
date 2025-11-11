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

public interface HistorialObserver {
    void actualizar(String evento, HistorialMedico h);
}