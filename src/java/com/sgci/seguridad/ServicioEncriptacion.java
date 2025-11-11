/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.seguridad;

/**
 *
 * @author Juan Pablo
 */
public interface ServicioEncriptacion {
    String encriptar(String data) throws Exception;
    String desencriptar(String data) throws Exception;
}
