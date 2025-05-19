/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto_github;

import com.formdev.flatlaf.FlatDarkLaf;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 *
 * @author victor
 */
public class VideoReproducido extends javax.swing.JFrame {

    /**
     * Creates new form VideoReproducido
     */
    private String currentVideoUrl;
    private int likes = 0;
    private int dislikes = 0;
    private int vistas = 0;
    private int usuarioId = 1; // Placeholder for current user ID
    private MediaPlayer mediaPlayer;
    private final JFXPanel fxPanel = new JFXPanel();
    private String videoTitulo;

    public VideoReproducido(String videoUrl) {
        initComponents();
        ReproductorDeVideo.setLayout(new BorderLayout());
        ReproductorDeVideo.add(fxPanel, BorderLayout.CENTER);
        cargarVideoData(videoUrl);
        cargarSugerencias();
        Like.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                likes++;
                ConteoLikes.setText(String.valueOf(likes));
                DatabaseManager.actualizarLikes(currentVideoUrl, likes);
            }
        });

        Dislike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dislikes++;
                ConteoDislikes.setText(String.valueOf(dislikes));
                DatabaseManager.actualizarDislikes(currentVideoUrl, dislikes);
            }
        });

        // Example of handling comment submission (you'll need a button)
        CajaComentarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comentario = CajaComentarios.getText();
                if (!comentario.trim().isEmpty()) {
                    DatabaseManager.guardarComentario(currentVideoUrl, usuarioId, comentario);
                    actualizarConteoComentarios();
                    CajaComentarios.setText(""); // Clear the comment box
                    // Optionally, load and display the new comments
                }
            }
        });
    }

    private void cargarVideoData(String videoUrl) {
    String[] detalles = DatabaseManager.obtenerDetallesVideo(videoUrl);
    if (detalles != null && detalles.length >= 6) {
        currentVideoUrl = detalles[0];
        NombreUsuario.setText(detalles[1]);

        // Verificación para 'vistas'
        String vistasStr = detalles[2]; // Obtenemos el valor como String
        if (vistasStr != null && !vistasStr.isEmpty()) { // Verificamos si no es nulo y no está vacío
            try {
                vistas = Integer.parseInt(vistasStr); // Intentamos convertir a entero
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir 'vistas' a entero: " + vistasStr);
                vistas = 0; // Asignamos un valor por defecto en caso de error de formato
            }
        } else {
            vistas = 0; // Si es nulo o vacío, asignamos 0
        }
        ConteoVistas.setText(vistas + " vistas");

        // Verificación para 'likes'
        String likesStr = detalles[3];
        if (likesStr != null && !likesStr.isEmpty()) {
            try {
                likes = Integer.parseInt(likesStr);
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir 'likes' a entero: " + likesStr);
                likes = 0;
            }
        } else {
            likes = 0;
        }
        ConteoLikes.setText(String.valueOf(likes));

        // Verificación para 'dislikes'
        String dislikesStr = detalles[4];
        if (dislikesStr != null && !dislikesStr.isEmpty()) {
            try {
                dislikes = Integer.parseInt(dislikesStr);
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir 'dislikes' a entero: " + dislikesStr);
                dislikes = 0;
            }
        } else {
            dislikes = 0;
        }
        ConteoDislikes.setText(String.valueOf(dislikes));

        videoTitulo = detalles[5];
        actualizarVistasInicial();
        cargarVideo(currentVideoUrl);
        actualizarConteoComentarios();
    } else {
        JOptionPane.showMessageDialog(this, "Error al cargar los detalles del video.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void actualizarVistasInicial() {
        vistas++;
        ConteoVistas.setText(vistas + " vistas");
        DatabaseManager.actualizarVistas(currentVideoUrl);
    }

    private void cargarVideo(String videoUrl) {
        Platform.runLater(() -> {
            Media media = new Media(videoUrl);
            mediaPlayer = new MediaPlayer(media);
            javafx.scene.Scene scene = new javafx.scene.Scene(new javafx.scene.layout.StackPane(new javafx.scene.media.MediaView(mediaPlayer)));
            fxPanel.setScene(scene);
            mediaPlayer.play();
        });
    }

    private void cargarSugerencias() {
        List<String[]> sugerencias = DatabaseManager.obtenerSugerenciasAleatorias(currentVideoUrl);
        MiniaturasLateral.removeAll();
        MiniaturasLateral.setLayout(new BoxLayout(MiniaturasLateral, BoxLayout.Y_AXIS));

        for (String[] data : sugerencias) {
            String thumbnailUrl = data[0];
            String suggestionUrl = data[1];
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setPreferredSize(new Dimension(MiniaturasLateral.getWidth() - 20, 100));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cargarImagenDesdeURL(thumbnailUrl, label);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cargarVideoData(suggestionUrl);
                    cargarSugerencias(); // Reload suggestions after a new video
                }
            });
            MiniaturasLateral.add(label);
            MiniaturasLateral.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        }
        MiniaturasLateral.revalidate();
        MiniaturasLateral.repaint();
    }

    private void cargarImagenDesdeURL(String url, JLabel label) {
        SwingUtilities.invokeLater(() -> {
            try {
                ImageIcon icon = new ImageIcon(new URL(url));
                Image image = icon.getImage().getScaledInstance(label.getWidth(), -1, Image.SCALE_SMOOTH); // Keep aspect ratio
                label.setIcon(new ImageIcon(image));
            } catch (Exception e) {
                e.printStackTrace();
                label.setText("Error al cargar la imagen");
            }
        });
    }

    private void actualizarConteoComentarios() {
        int conteo = DatabaseManager.obtenerConteoComentarios(currentVideoUrl);
        ConteoComentarios.setText(conteo + " Comentarios");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        TopMenu = new javax.swing.JPanel();
        BotonBuscar = new javax.swing.JButton();
        BarraBuscar = new javax.swing.JTextField();
        BotonMicrofono = new javax.swing.JButton();
        BotonYoutube = new javax.swing.JButton();
        BotonCampana = new javax.swing.JButton();
        BotonSubirVideo = new javax.swing.JButton();
        BotonPerfil = new javax.swing.JButton();
        ImagenTopMenu = new javax.swing.JLabel();
        Pagina = new javax.swing.JPanel();
        ReproductorDeVideo = new javax.swing.JPanel();
        Like = new javax.swing.JButton();
        Dislike = new javax.swing.JButton();
        Compartir = new javax.swing.JButton();
        Guardar = new javax.swing.JButton();
        BotonSuscribir = new javax.swing.JToggleButton();
        CajaComentarios = new javax.swing.JTextField();
        ConteoSuscriptores = new javax.swing.JLabel();
        NombreUsuario = new javax.swing.JLabel();
        ConteoLikes = new javax.swing.JLabel();
        ConteoDislikes = new javax.swing.JLabel();
        ConteoVistas = new javax.swing.JLabel();
        ConteoComentarios = new javax.swing.JLabel();
        TituloVideo = new javax.swing.JLabel();
        PaginaDiseño = new javax.swing.JLabel();
        MiniaturasLateral = new javax.swing.JPanel();
        Miniaturas = new javax.swing.JLabel();
        Fondo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TopMenu.setBackground(new java.awt.Color(33, 33, 33));
        TopMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BotonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonBuscar.png"))); // NOI18N
        BotonBuscar.setContentAreaFilled(false);
        BotonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 60, 40));

        BarraBuscar.setBackground(new java.awt.Color(18, 18, 18));
        BarraBuscar.setText("Buscar");
        BarraBuscar.setToolTipText("");
        BarraBuscar.setBorder(null);
        BarraBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TopMenu.add(BarraBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 520, 40));

        BotonMicrofono.setBackground(new java.awt.Color(0, 0, 0));
        BotonMicrofono.setBorder(null);
        BotonMicrofono.setBorderPainted(false);
        BotonMicrofono.setContentAreaFilled(false);
        BotonMicrofono.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonMicrofono, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 10, 40, 40));

        BotonYoutube.setContentAreaFilled(false);
        BotonYoutube.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonYoutube, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 40));

        BotonCampana.setBorder(null);
        BotonCampana.setBorderPainted(false);
        BotonCampana.setContentAreaFilled(false);
        BotonCampana.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonCampana, new org.netbeans.lib.awtextra.AbsoluteConstraints(1800, 15, 30, 30));

        BotonSubirVideo.setBorder(null);
        BotonSubirVideo.setBorderPainted(false);
        BotonSubirVideo.setContentAreaFilled(false);
        BotonSubirVideo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonSubirVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1750, 20, 30, 20));

        BotonPerfil.setBorder(null);
        BotonPerfil.setBorderPainted(false);
        BotonPerfil.setContentAreaFilled(false);
        BotonPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TopMenu.add(BotonPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1854, 10, 40, 40));

        ImagenTopMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TopMenu2.png"))); // NOI18N
        TopMenu.add(ImagenTopMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLayeredPane1.add(TopMenu);
        TopMenu.setBounds(0, 0, 1920, 60);

        Pagina.setBackground(new java.awt.Color(24, 24, 24));
        Pagina.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ReproductorDeVideo.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout ReproductorDeVideoLayout = new javax.swing.GroupLayout(ReproductorDeVideo);
        ReproductorDeVideo.setLayout(ReproductorDeVideoLayout);
        ReproductorDeVideoLayout.setHorizontalGroup(
            ReproductorDeVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        ReproductorDeVideoLayout.setVerticalGroup(
            ReproductorDeVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        Pagina.add(ReproductorDeVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 650, 370));

        Like.setBackground(new java.awt.Color(24, 24, 24));
        Like.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Like.png"))); // NOI18N
        Like.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Like.setContentAreaFilled(false);
        Like.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Like.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/LikePresionado.png"))); // NOI18N
        Pagina.add(Like, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, 30, 40));

        Dislike.setBackground(new java.awt.Color(24, 24, 24));
        Dislike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dislike.png"))); // NOI18N
        Dislike.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Dislike.setContentAreaFilled(false);
        Dislike.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Dislike.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/DislikePresionado.png"))); // NOI18N
        Dislike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DislikeActionPerformed(evt);
            }
        });
        Pagina.add(Dislike, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 446, 40, 30));

        Compartir.setContentAreaFilled(false);
        Compartir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Pagina.add(Compartir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 450, 120, 20));

        Guardar.setContentAreaFilled(false);
        Guardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Pagina.add(Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 450, 100, 20));

        BotonSuscribir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonSuscribir.png"))); // NOI18N
        BotonSuscribir.setBorderPainted(false);
        BotonSuscribir.setContentAreaFilled(false);
        BotonSuscribir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSuscribir.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonSuscrito.png"))); // NOI18N
        Pagina.add(BotonSuscribir, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 510, 110, 40));

        CajaComentarios.setBackground(new java.awt.Color(24, 24, 24));
        CajaComentarios.setBorder(null);
        Pagina.add(CajaComentarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 720, 570, 30));

        ConteoSuscriptores.setFont(new java.awt.Font("Noto Sans", 0, 10)); // NOI18N
        ConteoSuscriptores.setText(" suscriptores");
        Pagina.add(ConteoSuscriptores, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 530, 100, 20));
        Pagina.add(NombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, 300, 30));
        Pagina.add(ConteoLikes, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, 40, 30));
        Pagina.add(ConteoDislikes, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 440, 40, 30));

        ConteoVistas.setText(" vistas");
        Pagina.add(ConteoVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, 170, 30));

        ConteoComentarios.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        ConteoComentarios.setText(" Comentarios");
        Pagina.add(ConteoComentarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 659, 210, 30));
        Pagina.add(TituloVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 410, 650, 30));

        PaginaDiseño.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Principal.png"))); // NOI18N
        Pagina.add(PaginaDiseño, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        jLayeredPane1.add(Pagina);
        Pagina.setBounds(0, 60, 1290, 1020);

        MiniaturasLateral.setBackground(new java.awt.Color(24, 24, 24));
        MiniaturasLateral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Miniaturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MiniaturasLateralesPlaceholder.png"))); // NOI18N
        MiniaturasLateral.add(Miniaturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLayeredPane1.add(MiniaturasLateral);
        MiniaturasLateral.setBounds(1290, 60, 630, 1020);

        Fondo.setBackground(new java.awt.Color(24, 24, 24));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jLayeredPane1.add(Fondo);
        Fondo.setBounds(0, 0, 1920, 1080);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DislikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DislikeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DislikeActionPerformed

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
            java.util.logging.Logger.getLogger(VideoReproducido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoReproducido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoReproducido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoReproducido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Puedes usar FlatDarkLaf u otros temas de FlatLaf
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Platform.startup(() -> {});
       
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Esto ya no es necesario, la instancia se creará desde PaginaPrincipal
                // new VideoReproducido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BarraBuscar;
    private javax.swing.JButton BotonBuscar;
    private javax.swing.JButton BotonCampana;
    private javax.swing.JButton BotonMicrofono;
    private javax.swing.JButton BotonPerfil;
    private javax.swing.JButton BotonSubirVideo;
    private javax.swing.JToggleButton BotonSuscribir;
    private javax.swing.JButton BotonYoutube;
    private javax.swing.JTextField CajaComentarios;
    private javax.swing.JButton Compartir;
    private javax.swing.JLabel ConteoComentarios;
    private javax.swing.JLabel ConteoDislikes;
    private javax.swing.JLabel ConteoLikes;
    private javax.swing.JLabel ConteoSuscriptores;
    private javax.swing.JLabel ConteoVistas;
    private javax.swing.JButton Dislike;
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton Guardar;
    private javax.swing.JLabel ImagenTopMenu;
    private javax.swing.JButton Like;
    private javax.swing.JLabel Miniaturas;
    private javax.swing.JPanel MiniaturasLateral;
    private javax.swing.JLabel NombreUsuario;
    private javax.swing.JPanel Pagina;
    private javax.swing.JLabel PaginaDiseño;
    private javax.swing.JPanel ReproductorDeVideo;
    private javax.swing.JLabel TituloVideo;
    private javax.swing.JPanel TopMenu;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
