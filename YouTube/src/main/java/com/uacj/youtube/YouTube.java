/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.uacj.youtube;

/**
 *
 * @author victor
 */
import javax.swing.*;
import java.awt.*;

public class YouTube extends JFrame {
    public YouTube() {
        setTitle("YouTube Clone");
        setSize(800, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Cargar el logo de YouTube (debes proporcionar una ruta válida)
        ImageIcon logo = new ImageIcon("youtube_logo.png");
        JLabel logoLabel = new JLabel(logo);

        // Barra de búsqueda
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("Buscar");

        // Agregar componentes al panel
        panel.add(logoLabel);
        panel.add(searchField);
        panel.add(searchButton);

        // Agregar panel a la ventana
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new YouTube().setVisible(true);
        });
    }
}
