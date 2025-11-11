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
import com.sgci.repositorio.HistorialMedicoRepositorio;
import com.sgci.seguridad.ServicioEncriptacion;
import com.sgci.observer.HistorialObserver;
import java.util.ArrayList;
import java.util.List;

public class ServicioHistorial {
    protected final HistorialMedicoRepositorio repo = new HistorialMedicoRepositorio();
    protected final ServicioEncriptacion cifrador;
    protected final List<HistorialObserver> observadores = new ArrayList<>();

    public ServicioHistorial(ServicioEncriptacion cifrador) {
        this.cifrador = cifrador;
    }
    
    public ServicioEncriptacion getCifrador() {
        return cifrador;
    }


    public void agregarObservador(HistorialObserver o) {
        observadores.add(o);
    }

    protected void notificar(String evento, HistorialMedico h) {
        for (HistorialObserver o : observadores) {
            try { o.actualizar(evento, h); } catch (Exception ex) { System.err.println("Error observer: " + ex.getMessage()); }
        }
    }

    //Crea un historial: aplica cifrado (strategy)
    public void crearHistorial(HistorialMedico h) throws Exception {
        // cifrar campos sensibles
        if (h.getDiagnostico() != null) h.setDiagnostico(cifrador.encriptar(h.getDiagnostico()));
        if (h.getTratamiento() != null) h.setTratamiento(cifrador.encriptar(h.getTratamiento()));
        if (h.getNotas() != null) h.setNotas(cifrador.encriptar(h.getNotas()));
        repo.guardar(h);
        notificar("CREADO", h);
    }

    //Actualiza un historial
    public void actualizarHistorial(HistorialMedico h) throws Exception {
        if (h.getDiagnostico() != null) h.setDiagnostico(cifrador.encriptar(h.getDiagnostico()));
        if (h.getTratamiento() != null) h.setTratamiento(cifrador.encriptar(h.getTratamiento()));
        if (h.getNotas() != null) h.setNotas(cifrador.encriptar(h.getNotas()));
        repo.actualizar(h);
        notificar("ACTUALIZADO", h);
    }

    //Obtiene y desencripta los campos sensibles.
     
    public HistorialMedico obtenerPorId(int id) throws Exception {
        HistorialMedico h = repo.obtenerPorId(id);
        if (h == null) return null;
        if (h.getDiagnostico() != null) h.setDiagnostico(cifrador.desencriptar(h.getDiagnostico()));
        if (h.getTratamiento() != null) h.setTratamiento(cifrador.desencriptar(h.getTratamiento()));
        if (h.getNotas() != null) h.setNotas(cifrador.desencriptar(h.getNotas()));
        return h;
    }

    public java.util.List<HistorialMedico> listarPorPaciente(int idPaciente) throws Exception {
        java.util.List<HistorialMedico> lista = repo.listarPorPaciente(idPaciente);
        // desencriptar cada elemento
        for (HistorialMedico h : lista) {
            if (h.getDiagnostico() != null) h.setDiagnostico(cifrador.desencriptar(h.getDiagnostico()));
            if (h.getTratamiento() != null) h.setTratamiento(cifrador.desencriptar(h.getTratamiento()));
            if (h.getNotas() != null) h.setNotas(cifrador.desencriptar(h.getNotas()));
        }
        return lista;
    }
}