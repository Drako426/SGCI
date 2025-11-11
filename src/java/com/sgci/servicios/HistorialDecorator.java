/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.servicios;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;

public abstract class HistorialDecorator extends ServicioHistorial {
    protected final ServicioHistorial delegate;

    public HistorialDecorator(ServicioHistorial delegate) {
        super(delegate.cifrador); // reutilizamos el cifrador
        this.delegate = delegate;
    }

    @Override
    public void crearHistorial(HistorialMedico h) throws Exception {
        // delega la creación al servicio 
        delegate.crearHistorial(h);
        // sirve para que el decorator haga trabajo extra
        extraCrear(h);
    }

    //ejecutar acción después de crear un historial (por ejemplo: generar PDF, enviar email).
    protected abstract void extraCrear(HistorialMedico h) throws Exception;

    @Override
    public void actualizarHistorial(HistorialMedico h) throws Exception {
        delegate.actualizarHistorial(h);
        extraActualizar(h);
    }

    //acción extra tras actualizar. No es obligatorio implementarla (tiene implementación vacía por defecto).
    protected void extraActualizar(HistorialMedico h) throws Exception {
        // No es necesario por el momento que haga algo
    }
}