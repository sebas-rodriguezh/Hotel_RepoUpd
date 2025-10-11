USE hotel;

CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    primerApellido VARCHAR(100) NOT NULL,
    segundoApellido VARCHAR(100),
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    fechaNacimiento DATE NOT NULL
);

INSERT INTO cliente (nombre, primerApellido, segundoApellido, identificacion, fechaNacimiento) VALUES
('Juan', 'Pérez', 'Gómez', '123456789', '1990-05-15'),
('María', 'López', 'Hernández', '987654321', '1985-08-22'),
('Carlos', 'García', NULL, '456789123', '1992-11-30');

USE hotel;
SELECT * FROM cliente;