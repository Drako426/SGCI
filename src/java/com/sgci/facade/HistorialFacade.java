/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.facade;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;
import com.sgci.servicios.ServicioHistorial;
import java.util.List;

public class HistorialFacade {
    private final ServicioHistorial servicio;

    public HistorialFacade(ServicioHistorial servicio) {
        this.servicio = servicio;
    }

    public void crear(HistorialMedico h) throws Exception {
        servicio.crearHistorial(h);
    }

    public void actualizar(HistorialMedico h) throws Exception {
        servicio.actualizarHistorial(h);
    }

    public HistorialMedico obtener(int id) throws Exception {
        return servicio.obtenerPorId(id);
    }

    public List<HistorialMedico> listarPorPaciente(int idPaciente) throws Exception {
        return servicio.listarPorPaciente(idPaciente);
    }
}