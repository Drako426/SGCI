/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.servlet;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.HistorialMedico;
import com.sgci.repositorio.MedicoRepositorio;
import com.sgci.repositorio.PacienteRepositorio;
import com.sgci.seguridad.EncriptacionAES;
import com.sgci.seguridad.ServicioEncriptacion;
import com.sgci.servicios.EmailDecorator;
import com.sgci.servicios.PDFDecorator;
import com.sgci.servicios.ServicioHistorial;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CrearHistorialServlet", urlPatterns = "/historial/crear")
public class CrearHistorialServlet extends HttpServlet {

    private ServicioHistorial servicio;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            byte[] key = "0123456789abcdef".getBytes(); // 16 bytes ejemplo
            ServicioEncriptacion cifrador = new EncriptacionAES(key);
            ServicioHistorial base = new ServicioHistorial(cifrador);

            // registrar observadores
            base.agregarObservador(new com.sgci.observer.AuditoriaObserver());
            base.agregarObservador(new com.sgci.observer.NotificacionObserver());

            // envolver con decorators (PDF y Email)
            ServicioHistorial pdf = new PDFDecorator(base);
            ServicioHistorial email = new EmailDecorator(pdf);

            this.servicio = email; // email -> pdf -> base
        } catch (Exception ex) {
            throw new ServletException("Error init servicio historial: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/crearHistorial.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");

            PacienteRepositorio pacienteRepo = new PacienteRepositorio();
            MedicoRepositorio medicoRepo = new MedicoRepositorio();

            String cedula = req.getParameter("cedula");
            String nombrePaciente = req.getParameter("nombrePaciente");
            String nombreMedico = req.getParameter("nombreMedico");

            int idPaciente = -1;
            int idMedico = -1;

            // Buscar o crear paciente por cédula
            try {
                if (cedula != null && !cedula.trim().isEmpty()) {
                    com.sgci.modelo.Paciente p = pacienteRepo.buscarPorCedula(cedula.trim());
                    if (p != null) {
                        idPaciente = p.getId();
                        nombrePaciente = p.getNombre();
                    } else {
                        // crear paciente con cedula y nombre (si nombre vacío, guardar cedula como nombre temporal)
                        if (nombrePaciente == null || nombrePaciente.trim().isEmpty()) nombrePaciente = cedula.trim();
                        idPaciente = pacienteRepo.crearPaciente(nombrePaciente, cedula.trim());
                    }
                } else {
                    // si no hay cédula, intentar buscar por nombre
                    idPaciente = pacienteRepo.obtenerIdPorNombre(nombrePaciente);
                    if (idPaciente == -1) {
                        idPaciente = pacienteRepo.crearPaciente(nombrePaciente, "");
                    }
                }

                idMedico = medicoRepo.obtenerIdPorNombre(nombreMedico);
                if (idMedico == -1) {
                    idMedico = medicoRepo.crearMedico(nombreMedico);
                }
            } catch (Exception e) {
                throw new ServletException("Error al acceder a la base de datos", e);
            }

            // --- crear historial ---
            HistorialMedico h = new HistorialMedico();
            h.setIdPaciente(idPaciente);
            h.setIdMedico(idMedico);

            String fecha = req.getParameter("fechaConsulta");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            h.setFechaConsulta(new java.util.Date(sdf.parse(fecha).getTime()));

            h.setDiagnostico(req.getParameter("diagnostico"));
            h.setTratamiento(req.getParameter("tratamiento"));
            h.setNotas(req.getParameter("notas"));
            h.setCreadoPor(idMedico); // médico creador
            h.setEstadoActual(req.getParameter("estadoActual"));

            // rellenar campos auxiliares para sesión / listado
            h.setNombrePaciente(nombrePaciente);
            h.setCedulaPaciente((cedula != null) ? cedula.trim() : "");

            // guardar (ServicioHistorial se encargará de cifrar y persistir)
            servicio.crearHistorial(h);

            // Guardar en la sesión el historial creado (solo para esta ejecución)
            HttpSession session = req.getSession(true);
            List<HistorialMedico> lista = (List<HistorialMedico>) session.getAttribute("historialesSesion");
            if (lista == null) {
                lista = new ArrayList<>();
            }
            lista.add(h);
            session.setAttribute("historialesSesion", lista);

            resp.sendRedirect(req.getContextPath() + "/historial/listado?busqueda=" + (h.getCedulaPaciente() != null ? h.getCedulaPaciente() : ""));
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}