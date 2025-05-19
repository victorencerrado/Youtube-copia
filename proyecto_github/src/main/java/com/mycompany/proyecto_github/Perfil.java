package com.mycompany.proyecto_github;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Perfil extends JFrame {
    private String username;
    private JTabbedPane tabbedPane;
    private JPanel videosPanel;

    public Perfil(String username) {
        this.username = username;
        setTitle("Perfil de " + username);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Panel de Videos
        videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        cargarVideos();
        JScrollPane scrollPane = new JScrollPane(videosPanel);
        tabbedPane.addTab("Videos", scrollPane);

        // Panel de Información
        JPanel infoPanel = crearPanelInformacion();
        tabbedPane.addTab("Información", infoPanel);

        // Panel de Listas (placeholder)
        JPanel listasPanel = new JPanel();
        listasPanel.add(new JLabel("Listas de reproducción próximamente."));
        tabbedPane.addTab("Listas", listasPanel);

        // Panel de Canales (placeholder)
        JPanel canalesPanel = new JPanel();
        canalesPanel.add(new JLabel("Canales suscritos próximamente."));
        tabbedPane.addTab("Canales", canalesPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private void cargarVideos() {
        ArrayList<Video> videos = DatabaseManager.obtenerVideosPorUsuario(username);
        videosPanel.removeAll();
        for (Video video : videos) {
            VideoPanel panel = new VideoPanel(video);
            videosPanel.add(panel);
        }
        videosPanel.revalidate();
        videosPanel.repaint();
    }

    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        try (Connection conn = DatabaseManager.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre, descripcion FROM usuarios WHERE nombre_usuario = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");

                panel.add(new JLabel("Nombre: " + nombre));
                panel.add(new JLabel("Descripción: " + (descripcion != null ? descripcion : "Sin descripción")));
            } else {
                panel.add(new JLabel("Usuario no encontrado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panel;
    }
}

    public Perfil() {
        initComponents();
      
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        Videos = new javax.swing.JPanel();
        NombreUsuarioPerfil = new javax.swing.JLabel();
        BackgroundPhoto = new javax.swing.JLabel();
        TítuloDeVideo = new javax.swing.JLabel();
        Video = new javax.swing.JPanel();
        Youtuber = new javax.swing.JLabel();
        TítuloDeVideo1 = new javax.swing.JLabel();
        Vistas = new javax.swing.JLabel();
        Vistas1 = new javax.swing.JLabel();
        Youtuber1 = new javax.swing.JLabel();
        Video1 = new javax.swing.JPanel();
        Video2 = new javax.swing.JPanel();
        Video3 = new javax.swing.JPanel();
        Video5 = new javax.swing.JPanel();
        Video6 = new javax.swing.JPanel();
        Fondo = new javax.swing.JPanel();
        PerfilFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Videos.setBackground(new java.awt.Color(33, 33, 33));
        Videos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Videos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        NombreUsuarioPerfil.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        NombreUsuarioPerfil.setForeground(new java.awt.Color(255, 255, 255));
        NombreUsuarioPerfil.setText("High Boi");
        Videos.add(NombreUsuarioPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 710, 30));

        BackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PerfilYoutube.png"))); // NOI18N
        Videos.add(BackgroundPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        TítuloDeVideo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        TítuloDeVideo.setForeground(new java.awt.Color(255, 255, 255));
        TítuloDeVideo.setText("Hace 1 año");
        Videos.add(TítuloDeVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 440, 170, 20));
        Videos.add(Video, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 340, 200));

        Youtuber.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        Youtuber.setForeground(new java.awt.Color(255, 255, 255));
        Youtuber.setText("Videos");
        Videos.add(Youtuber, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 700, 540, 30));

        TítuloDeVideo1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        TítuloDeVideo1.setForeground(new java.awt.Color(255, 255, 255));
        TítuloDeVideo1.setText("High Boi");
        Videos.add(TítuloDeVideo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 440, 100, 20));

        Vistas.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Vistas.setForeground(new java.awt.Color(255, 255, 255));
        Vistas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Vistas.setLabelFor(Video);
        Vistas.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas fermentum lacinia mauris vel ultrices. Aliquam tempus velit sit amet vehicula pellentesque. Donec ac ipsum ante. Praesent in tellus id enim venenatis porttitor. Nulla imperdiet ornare neque ac dignissim. Duis tempus est ac magna vestibulum, a facilisis neque tincidunt. Donec quis dui turpis. Fusce aliquet, dui et hendrerit hendrerit, nunc orci maximus dolor, at congue metus risus a augue. Mauris semper a massa id consectetur. Cras commodo mauris eget justo venenatis ultrices. Cras imperdiet et lacus quis interdum. Vivamus feugiat eros sollicitudin, venenatis mi quis, mollis lacus. Donec ac nunc eget dui tempor fermentum.");
        Vistas.setToolTipText("");
        Vistas.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Vistas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Vistas.setFocusable(false);
        Vistas.setName(""); // NOI18N
        Vistas.setNextFocusableComponent(Video);
        Videos.add(Vistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, 530, 120));

        Vistas1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Vistas1.setForeground(new java.awt.Color(255, 255, 255));
        Vistas1.setText("18M de vistas");
        Videos.add(Vistas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 440, 170, 20));

        Youtuber1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        Youtuber1.setForeground(new java.awt.Color(255, 255, 255));
        Youtuber1.setText("Título de video");
        Videos.add(Youtuber1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 410, 540, 30));
        Videos.add(Video1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 230, 140));
        Videos.add(Video2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 730, 230, 140));
        Videos.add(Video3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 730, 230, 140));
        Videos.add(Video5, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 730, 230, 140));
        Videos.add(Video6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 730, 230, 140));

        jLayeredPane1.add(Videos);
        Videos.setBounds(240, 50, 1680, 1030);

        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PerfilFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PerfilFondo.png"))); // NOI18N
        Fondo.add(PerfilFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLayeredPane1.add(Fondo);
        Fondo.setBounds(0, 0, 1920, 1080);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Perfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Perfil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundPhoto;
    private javax.swing.JPanel Fondo;
    private javax.swing.JLabel NombreUsuarioPerfil;
    private javax.swing.JLabel PerfilFondo;
    private javax.swing.JLabel TítuloDeVideo;
    private javax.swing.JLabel TítuloDeVideo1;
    private javax.swing.JPanel Video;
    private javax.swing.JPanel Video1;
    private javax.swing.JPanel Video2;
    private javax.swing.JPanel Video3;
    private javax.swing.JPanel Video5;
    private javax.swing.JPanel Video6;
    private javax.swing.JPanel Videos;
    private javax.swing.JLabel Vistas;
    private javax.swing.JLabel Vistas1;
    private javax.swing.JLabel Youtuber;
    private javax.swing.JLabel Youtuber1;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
