/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgci.repositorio;

/**
 *
 * @author Juan Pablo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexion {

    // Ajusta estos datos según tu entorno
    private static final String URL = "jdbc:mysql://localhost:3307/sgci?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASS = "";

    private static DBConexion instancia;

    private DBConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC de MySQL", e);
        }
    }

    public static synchronized DBConexion getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new DBConexion();
        }
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}