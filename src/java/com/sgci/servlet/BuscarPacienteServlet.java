/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.servlet;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.repositorio.PacienteRepositorio;
import com.sgci.modelo.Paciente;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "BuscarPacienteServlet", urlPatterns = {"/buscarPaciente"})
public class BuscarPacienteServlet extends HttpServlet {

    private final PacienteRepositorio repo = new PacienteRepositorio();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String cedula = req.getParameter("cedula");
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Paciente paciente = repo.buscarPorCedula(cedula);

            String json;
            if (paciente != null) {
                json = "{"
                        + "\"id\":" + paciente.getId() + ","
                        + "\"nombre\":\"" + escapeJson(paciente.getNombre()) + "\","
                        + "\"cedula\":\"" + escapeJson(paciente.getNumeroIdentificacion()) + "\""
                        + "}";
            } else {
                json = "{\"id\":0,\"nombre\":\"\",\"cedula\":\"\"}";
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
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}