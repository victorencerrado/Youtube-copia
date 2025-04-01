CREATE DATABASE DB_Youtube;
USE DB_Youtube;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100),
    contrasena VARCHAR(255)
);

CREATE TABLE videos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200),
    url VARCHAR(255),
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto TEXT,
    video_id INT,
    FOREIGN KEY (video_id) REFERENCES videos(id)
);
