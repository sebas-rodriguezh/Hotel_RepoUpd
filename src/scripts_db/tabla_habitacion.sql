USE hotel;

CREATE TABLE habitacion (
id INT PRIMARY KEY AUTO_INCREMENT,
numero INT NOT NULL UNIQUE, 
tipo INT NOT NULL, 
estado INT NOT NULL, 
precio DECIMAL(10,2) NOT NULL, 
capacidad INT NOT NULL
);

INSERT INTO habitacion (numero, tipo, estado, precio, capacidad) VALUES
(101, 1, 1, 100.00, 2), 
(102, 1, 1, 100.00, 2), 
(201, 2, 1, 200.00, 4),
(202, 2, 2, 200.00, 4), 
(103, 1, 3, 100.00, 2); 

USE hotel;
SELECT * FROM habitacion