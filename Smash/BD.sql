CREATE DATABASE Smash ; 
USE Smash;

CREATE TABLE Personaje(
P_id INT  PRIMARY KEY AUTO_INCREMENT,
P_nombre VARCHAR(15) NOT NULL ,
P_tipo VARCHAR(15) NOT NULL ,
P_vida INT  NOT NULL,
P_resistencia INT NOT NULL ,
P_alcanze INT NOT NULL,
P_habilidad VARCHAR(15) NOT NULL
);

DESCRIBE  TABLE Personaje;
DROP TABLE Personaje ;
SELECT * FROM Personaje ;
INSERT INTO Personaje (
    P_nombre, P_tipo, P_vida, P_resistencia, P_alcanze, P_habilidad
) VALUES (
    'Ezio', 'Mago', 100, 50, 30, 'Invisibilidad'
);
