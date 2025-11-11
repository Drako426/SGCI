/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.seguridad;

/**
 *
 * @author Juan Pablo
 */
import java.util.Base64;

public class EncriptacionBase64 implements ServicioEncriptacion {

    @Override
    public String encriptar(String data) throws Exception {
        if (data == null) return null;
        return Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
    }

    @Override
    public String desencriptar(String data) throws Exception {
        if (data == null) return null;
        return new String(Base64.getDecoder().decode(data), "UTF-8");
    }
}