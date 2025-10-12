USE hotel;

CREATE TABLE reservacion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT NOT NULL,
    habitacion_id INT NOT NULL,
    fecha_llegada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    cantidad_noches INT NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (habitacion_id) REFERENCES habitacion(id)
);

USE hotel;
SELECT * FROM reservacion; 