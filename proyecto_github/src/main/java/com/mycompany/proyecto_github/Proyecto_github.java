//Esto da el alignment y el color a los elementos de la interfaz

package com.mycompany.proyecto_github;

import javax.swing.*;
import java.awt.*;

public class Proyecto_github extends javax.swing.JFrame {
    
   public Proyecto_github() {
    initComponents(); // Mantiene el código generado
    customInitComponents(); // Agrega los nuevos componentes
}

    private void customInitComponents() {
    setTitle("Simulación de Interfaz YouTube");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Barra superior (Header)
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(new Color(255, 0, 0)); // Color rojo YouTube
    topPanel.setPreferredSize(new Dimension(1200, 60));

    // Logo de YouTube (texto)
    JLabel logoLabel = new JLabel("YouTube");
    logoLabel.setForeground(Color.WHITE);
    logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
    logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

    // Barra de búsqueda
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchPanel.setOpaque(false);
    JTextField searchField = new JTextField(40);
    JButton searchButton = new JButton("Buscar");
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    topPanel.add(logoLabel, BorderLayout.WEST);
    topPanel.add(searchPanel, BorderLayout.CENTER);

    // Barra lateral
    JPanel sidebarPanel = new JPanel();
    sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
    sidebarPanel.setPreferredSize(new Dimension(200, 800));

    String[] navButtons = {"Inicio", "Tendencias", "Suscripciones", "Biblioteca"};
    for (String text : navButtons) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(btn);
        sidebarPanel.add(Box.createVerticalStrut(10));
    }

    // Contenido principal (Videos)
    JPanel mainContentPanel = new JPanel(new GridLayout(0, 3, 10, 10));
    for (int i = 1; i <= 9; i++) {
        JPanel videoPanel = new JPanel(new BorderLayout());
        videoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel thumbnail = new JLabel("Miniatura " + i, SwingConstants.CENTER);
        thumbnail.setPreferredSize(new Dimension(200, 120));
        videoPanel.add(thumbnail, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Título del video " + i, SwingConstants.CENTER);
        videoPanel.add(titleLabel, BorderLayout.SOUTH);

        mainContentPanel.add(videoPanel);
    }

    JScrollPane scrollPane = new JScrollPane(mainContentPanel);

    // Agregar los paneles
    add(topPanel, BorderLayout.NORTH);
    add(sidebarPanel, BorderLayout.WEST);
    add(scrollPane, BorderLayout.CENTER);
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Proyecto_github.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proyecto_github.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proyecto_github.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proyecto_github.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proyecto_github().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

//topPanel.add(logoLabel, BorderLayout.WEST);
  //  topPanel.add(searchPanel, BorderLayout.CENTER);

// fin de codigo

//Integrantes 
//Victor Manuel Encerrado Solares - 216872
//Axel Gamaliel Aragon Garcia - 215926
//Samuel Morgado Guatemala - 215551
//Dante Uriel Ramirez Marquez - 227481
//Luis Yair Rodriguez Recio - 215728