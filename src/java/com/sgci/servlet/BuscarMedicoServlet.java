/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.servlet;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.repositorio.MedicoRepositorio;
import com.sgci.modelo.Medico;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "BuscarMedicoServlet", urlPatterns = {"/buscarMedico"})
public class BuscarMedicoServlet extends HttpServlet {

    private final MedicoRepositorio repo = new MedicoRepositorio();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Medico medico = repo.buscarPorNombre((nombre != null) ? nombre.trim() : null);
            String json;
            if (medico != null) {
                json = "{"
                        + "\"id\":" + medico.getId() + ","
                        + "\"nombre\":\"" + escapeJson(medico.getNombre()) + "\","
                        + "\"especialidad\":\"" + escapeJson(medico.getEspecialidad()) + "\""
                        + "}";
            } else {
                json = "{\"id\":0,\"nombre\":\"\",\"especialidad\":\"\"}";
            }
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}