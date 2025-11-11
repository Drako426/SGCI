/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.factory;

/**
 *
 * @author Juan Pablo
 */
import com.sgci.modelo.Usuario;
import com.sgci.modelo.Paciente;
import com.sgci.modelo.Medico;
import com.sgci.modelo.Administrador;

public class UsuarioFactory {

    public static Usuario crearUsuario(String tipo, String nombre) {
        if ("paciente".equalsIgnoreCase(tipo)) {
            Paciente p = new Paciente();
            p.setNombre(nombre);
            p.setRol("paciente");
            return p;
        } else if ("medico".equalsIgnoreCase(tipo)) {
            Medico m = new Medico();
            m.setNombre(nombre);
            m.setRol("medico");
            return m;
        } else {
            Administrador a = new Administrador();
            a.setNombre(nombre);
            a.setRol("administrador");
            return a;
        }
    }
}