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
import com.sgci.seguridad.EncriptacionAES;
import com.sgci.seguridad.ServicioEncriptacion;
import com.sgci.servicios.ServicioHistorial;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "EditarHistorialServlet", urlPatterns = "/historial/editar")
public class EditarHistorialServlet extends HttpServlet {

    private ServicioHistorial servicio;

    @Override
    public void init() throws ServletException {
        try {
            byte[] key = "0123456789abcdef".getBytes();
            ServicioEncriptacion cifrador = new EncriptacionAES(key);
            servicio = new ServicioHistorial(cifrador);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            HistorialMedico h = servicio.obtenerPorId(id);
            req.setAttribute("historial", h);
            req.getRequestDispatcher("/editarHistorial.jsp").forward(req, resp);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        try {
            HistorialMedico h = new HistorialMedico();
            h.setIdHistorial(Integer.parseInt(req.getParameter("idHistorial")));
            h.setDiagnostico(req.getParameter("diagnostico"));
            h.setTratamiento(req.getParameter("tratamiento"));
            h.setNotas(req.getParameter("notas"));
            h.setEsBorrador("on".equals(req.getParameter("esBorrador")));
            h.setIdPaciente(Integer.parseInt(req.getParameter("idPaciente")));
            servicio.actualizarHistorial(h);
            resp.sendRedirect(req.getContextPath() + "/historial/listar?idPaciente=" + h.getIdPaciente());
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}