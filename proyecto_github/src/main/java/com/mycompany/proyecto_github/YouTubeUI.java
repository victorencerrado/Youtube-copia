package com.mycompany.proyecto_github;

import javax.swing.*;
import java.awt.*;

public class YouTubeUI extends JFrame {

    public YouTubeUI() {
        initComponents();
    }
    
    private void initComponents() {
        // Configuración básica del JFrame
        setTitle("Simulación de Interfaz YouTube");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 1. Barra superior (Header)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 0, 0)); // Color rojo característico de YouTube
        topPanel.setPreferredSize(new Dimension(1200, 60));
        
        // Logo de YouTube (texto para este ejemplo)
        JLabel logoLabel = new JLabel("YouTube");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        // Panel de búsqueda en la barra superior
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);  // Para mantener el fondo rojo del topPanel
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        topPanel.add(logoLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        // 2. Barra lateral (Sidebar) con botones de navegación
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
        
        // 3. Contenido principal: área de videos
        JPanel mainContentPanel = new JPanel(new GridLayout(0, 3, 10, 10));  // Distribuye los videos en 3 columnas
        
        // Ejemplo: se crean 9 "tarjetas" de video
        for (int i = 1; i <= 9; i++) {
            JPanel videoPanel = new JPanel(new BorderLayout());
            videoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            // Simulación de miniatura del video
            JLabel thumbnail = new JLabel("Miniatura " + i, SwingConstants.CENTER);
            thumbnail.setPreferredSize(new Dimension(200, 120));
            videoPanel.add(thumbnail, BorderLayout.CENTER);
            
            // Título del video
            JLabel titleLabel = new JLabel("Título del video " + i, SwingConstants.CENTER);
            videoPanel.add(titleLabel, BorderLayout.SOUTH);
            
            mainContentPanel.add(videoPanel);
        }
        
        // Se utiliza un JScrollPane para permitir desplazamiento en el contenido principal
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        
        // Agregar todos los paneles al JFrame
        add(topPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        // Se asegura que la interfaz gráfica se ejecute en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new YouTubeUI().setVisible(true);
        });
    }
}
