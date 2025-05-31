DROP DATABASE IF EXISTS hotel_reservas;
CREATE DATABASE hotel_reservas CHARACTER SET utf8mb4;

USE hotel_reservas;



CREATE TABLE habitaciones (
    id_habitacion INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT,
    precio_noche DECIMAL(10,2) NOT NULL,
    capacidad INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    imagen VARCHAR(255)
);



CREATE TABLE usuario(
	id_usuario INT AUTO_INCREMENT PRIMARY KEY ,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20)
);
CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_habitacion INT NOT NULL,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    anticipo DECIMAL(10,2) DEFAULT 0,
    estado ENUM('Pendiente', 'Confirmada', 'Cancelada') DEFAULT 'Pendiente',
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion)
);

INSERT INTO usuario(nombre, apellidos, username, password, email, telefono) VALUES ("lisa","voloshchuk", "lisank", "1234", "lizank@gmail.com", "093628313");
SELECT *
FROM usuario;
use hotel_reservas;
ALTER TABLE usuario ADD COLUMN rol VARCHAR(20) DEFAULT 'cliente';
ALTER TABLE habitaciones DROP COLUMN huespedes;
ALTER TABLE reservas ADD COLUMN huespedes SMALLINT DEFAULT "1";
ALTER TABLE habitaciones ADD COLUMN numero SMALLINT ;
INSERT INTO usuario (nombre, apellidos, username, password, rol, email,telefono)
VALUES ('Admin', 'Principal', 'admin', 'admin123', 'admin', 'admin@hotel.es','73477390801');
-- Habitaciones estándar
INSERT INTO habitaciones (tipo, descripcion, precio_noche, capacidad, disponible, imagen) VALUES
('Estándar', 'Habitación estándar con cama doble y baño privado.', 60.00, 2, TRUE, 'img/estandar1.jpg'),
('Estándar', 'Habitación cómoda con escritorio y TV.', 62.00, 2, TRUE, 'img/estandar2.jpg'),
('Estándar', 'Estándar con opción de camas separadas.', 58.00, 2, TRUE, 'img/estandar3.jpg'),
('Estándar', 'Habitación sencilla ideal para estancias cortas.', 55.00, 1, TRUE, 'img/estandar4.jpg'),
('Estándar', 'Estándar con vista al patio interior.', 63.00, 2, TRUE, 'img/estandar5.jpg');

-- Habitaciones deluxe
INSERT INTO habitaciones (tipo, descripcion, precio_noche, capacidad, disponible, imagen) VALUES
('Deluxe', 'Habitación deluxe con vista al mar y cama king size.', 120.00, 2, TRUE, 'img/deluxe1.jpg'),
('Deluxe', 'Deluxe con bañera hidromasaje y minibar.', 130.00, 2, TRUE, 'img/deluxe2.jpg'),
('Deluxe', 'Espaciosa deluxe con balcón privado.', 125.00, 3, TRUE, 'img/deluxe3.jpg');

-- Habitaciones presidenciales
INSERT INTO habitaciones (tipo, descripcion, precio_noche, capacidad, disponible, imagen) VALUES
('Presidencial', 'Suite presidencial con sala de estar, cocina y jacuzzi.', 250.00, 4, TRUE, 'img/presidencial1.jpg'),
('Presidencial', 'Suite premium con dos habitaciones, comedor y terraza.', 270.00, 5, TRUE, 'img/presidencial2.jpg');
UPDATE habitaciones
SET imagen = CONCAT('/', imagen)
WHERE imagen NOT LIKE '/%';
UPDATE habitaciones SET numero = 101 WHERE imagen = '/img/estandar1.jpg';
UPDATE habitaciones SET numero = 102 WHERE imagen = '/img/estandar2.jpg';
UPDATE habitaciones SET numero = 103 WHERE imagen = '/img/estandar3.jpg';
UPDATE habitaciones SET numero = 104 WHERE imagen = '/img/estandar4.jpg';
UPDATE habitaciones SET numero = 105 WHERE imagen = '/img/estandar5.jpg';

UPDATE habitaciones SET numero = 201 WHERE imagen = '/img/deluxe1.jpg';
UPDATE habitaciones SET numero = 202 WHERE imagen = '/img/deluxe2.jpg';
UPDATE habitaciones SET numero = 203 WHERE imagen = '/img/deluxe3.jpg';

UPDATE habitaciones SET numero = 301 WHERE imagen = '/img/presidencial1.jpg';
UPDATE habitaciones SET numero = 302 WHERE imagen = '/img/presidencial2.jpg';
SELECT *
FROM reservas;
SELECT r.*, u.nombre, u.apellidos, h.numero, h.tipo 
FROM reservas r 
JOIN usuario u ON r.id_usuario = u.id_usuario 
JOIN habitaciones h ON r.id_habitacion = h.id_habitacion 
WHERE LOWER(u.nombre) LIKE '%lisa%' 
   OR LOWER(u.apellidos) LIKE '%lisa%' 
   OR LOWER(h.numero) LIKE '%lisa%' 
   OR LOWER(h.tipo) LIKE '%lisa%';