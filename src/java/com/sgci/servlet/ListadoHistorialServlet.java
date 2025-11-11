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
import com.sgci.repositorio.HistorialMedicoRepositorio;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ListadoHistorialServlet", urlPatterns = "/historial/listado")
public class ListadoHistorialServlet extends HttpServlet {
    private HistorialMedicoRepositorio repo = new HistorialMedicoRepositorio();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busqueda = req.getParameter("busqueda");
        String busquedaTrim = (busqueda != null) ? busqueda.trim() : "";
        List<HistorialMedico> resultados = new ArrayList<>();

        try {
            if (!busquedaTrim.isEmpty()) {
                resultados.addAll(repo.buscarPorCedula(busquedaTrim));
            }
        } catch (Exception e) {
            throw new ServletException("Error al buscar historiales en BD: " + e.getMessage(), e);
        }

        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            List<HistorialMedico> creadosSesion = (List<HistorialMedico>) sesion.getAttribute("historialesSesion");
            if (creadosSesion != null) {
                for (HistorialMedico h : creadosSesion) {
                    boolean match = busquedaTrim.isEmpty()
                            || (h.getNombrePaciente() != null && h.getNombrePaciente().toLowerCase().contains(busquedaTrim.toLowerCase()))
                            || (h.getCedulaPaciente() != null && h.getCedulaPaciente().equalsIgnoreCase(busquedaTrim));
                    if (match) resultados.add(h);
                }
            }
        }

        req.setAttribute("lista", resultados);
        req.getRequestDispatcher("/listaHistoriales.jsp").forward(req, resp);
    }
}
