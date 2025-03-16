/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_github;

/**
 *
 * @author victor
 */
public class Proyecto_github {
     public static void main(String[] args) {
        System.out.println("Ejecutando Proyecto_github...");
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearCuenta().setVisible(true);
            }
        });
    }
}
