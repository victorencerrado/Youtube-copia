/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.proyecto_github;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Victor Encerrado al216872@alumnos.uacj.mx
 */
public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USER = "root";
    private static final String PASSWORD = "hola12345";

    public static List<String> obtenerMiniaturasAleatorias() {
        List<String> miniaturas = new ArrayList<>();
        String query = "SELECT url_miniatura FROM miniaturas ORDER BY RAND() LIMIT 12";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                miniaturas.add(rs.getString("url_miniatura"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return miniaturas;
    }
    
    public static List<String> obtenerTitulosAleatorios() {
        List<String> titulos = new ArrayList<>();
        String query = "SELECT titulo FROM video_juegos ORDER BY RAND() LIMIT 12";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                titulos.add(rs.getString("titulo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return titulos;
    }
    
    public static List<String[]> obtenerMiniaturasAleatoriasConUrls() {
        List<String[]> miniaturasConUrls = new ArrayList<>();
        String query = "SELECT url_miniatura, url_video FROM miniaturas ORDER BY RAND() LIMIT 12";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
}
