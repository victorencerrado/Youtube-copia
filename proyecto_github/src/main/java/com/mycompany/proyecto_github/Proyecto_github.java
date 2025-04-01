package com.mycompany.proyecto_github;

public class Proyecto_github {
     public static void main(String[] args) {
        System.out.println("Ejecutando Proyecto_github...");

          db_class db = new db_class();
          db.conectar();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearCuenta().setVisible(true);
            }
        });
    }
}
