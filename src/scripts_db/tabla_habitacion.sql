USE hotel;

CREATE TABLE habitacion (
id INT PRIMARY KEY AUTO_INCREMENT,
numero INT NOT NULL UNIQUE, 
tipo INT NOT NULL, -- 1: Standard, 2: Suite
estado INT NOT NULL, -- 1: Perfecto, 2: Mantenimiento, 3: Reservada, 4: Clausurada, 5: Ocupada
precio DECIMAL(10,2) NOT NULL, 
capacidad INT NOT NULL
);

INSERT INTO habitacion (numero, tipo, estado, precio, capacidad) VALUES
(101, 1, 1, 100.00, 2), -- Disponible (estado = 1)
(102, 1, 1, 100.00, 2), -- Disponible (estado = 1)
(201, 2, 1, 200.00, 4), -- Disponible (estado = 1)
(202, 2, 2, 200.00, 4), -- No disponible (estado = 2)
(103, 1, 3, 100.00, 2); -- No disponible (estado = 3)

USE hotel;
SELECT * FROM habitacion