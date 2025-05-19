/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.proyecto_github;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author Victor Encerrado al216872@alumnos.uacj.mx
 */
public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USER = "root";
    private static final String PASSWORD = "hola12345";
    private static final Random random = new Random();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<String[]> obtenerMiniaturasAleatoriasConUrls() {
        List<String[]> miniaturasConUrls = new ArrayList<>();
        // **MODIFICACIÓN AQUÍ: Seleccionar desde la tabla 'miniaturas'**
        String query = "SELECT url_miniatura, url_video FROM miniaturas ORDER BY RAND() LIMIT 12";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String thumbnailUrl = rs.getString("url_miniatura");
                String videoUrl = rs.getString("url_video");
                miniaturasConUrls.add(new String[]{thumbnailUrl, videoUrl});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return miniaturasConUrls;
    }

    public static String[] obtenerDetallesVideo(String videoUrl) {
        String[] detalles = new String[6]; // [url_video, nombre_usuario, vistas, likes, dislikes, titulo]
        String query = "SELECT v.url_video, u.email, v.vistas, v.likes, v.dislikes, v.titulo " +
                       "FROM videos v JOIN usuarios u ON v.autor_id = u.id " +
                       "WHERE v.url_video = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, videoUrl);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                detalles[0] = rs.getString("url_video");
                detalles[1] = rs.getString("email");
                detalles[2] = String.valueOf(rs.getInt("vistas"));
                detalles[3] = String.valueOf(rs.getInt("likes"));
                detalles[4] = String.valueOf(rs.getInt("dislikes"));
                detalles[5] = rs.getString("titulo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    public static void actualizarVistas(String videoUrl) {
        String query = "UPDATE videos SET vistas = vistas + 1 WHERE url_video = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, videoUrl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void actualizarLikes(String videoUrl, int likes) {
        String query = "UPDATE videos SET likes = ? WHERE url_video = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, likes);
            stmt.setString(2, videoUrl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void actualizarDislikes(String videoUrl, int dislikes) {
        String query = "UPDATE videos SET dislikes = ? WHERE url_video = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dislikes);
            stmt.setString(2, videoUrl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void guardarComentario(String videoUrl, int usuarioId, String texto) {
        String query = "INSERT INTO comentarios (video_id, usuario_id, texto) " +
                       "SELECT v.id, ?, ? FROM videos v WHERE v.url_video = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, usuarioId); // Assuming you have a way to get the current user's ID
            stmt.setString(2, texto);
            stmt.setString(3, videoUrl);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerConteoComentarios(String videoUrl) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM comentarios c JOIN videos v ON c.video_id = v.id WHERE v.url_video = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, videoUrl);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static List<String[]> obtenerSugerenciasAleatorias(String currentVideoUrl) {
        List<String[]> sugerencias = new ArrayList<>();
        // Example query: Get 5 random videos that are not the current one
        String query = "SELECT url_miniatura, url_video FROM videos WHERE url_video != ? ORDER BY RAND() LIMIT 5";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, currentVideoUrl);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String thumbnailUrl = rs.getString("url_miniatura");
                String videoUrl = rs.getString("url_video");
                sugerencias.add(new String[]{thumbnailUrl, videoUrl});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sugerencias;
    }
}

