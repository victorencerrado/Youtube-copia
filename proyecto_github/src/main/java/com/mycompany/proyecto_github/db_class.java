/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_github;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dante
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class db_class {
    String constring = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL";
   
   public void conectar(){
       
        try {
            Connection cn = DriverManager.getConnection(constring, "root", "hola12345");
            System.out.print("Conexion exitosa");
        } catch (SQLException ex) {
            Logger.getLogger(db_class.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Conexion falla");
            System.out.print(ex.getMessage());
        }
   }
    
}
