CREATE DATABASE IF NOT EXISTS reservasys;
USE reservasys;

-- Tabla de restaurantes
CREATE TABLE restaurantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    nombre VARCHAR(255) NOT NULL,         
    direccion VARCHAR(255) NOT NULL,      
    telefono VARCHAR(15) NOT NULL,        
    hora_apertura TIME NOT NULL,          
    hora_cierre TIME NOT NULL             
);

-- Tabla de mesas
CREATE TABLE mesas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                
    codigo_mesa VARCHAR(50) UNIQUE,                      
    tipo_mesa VARCHAR(50) NOT NULL,                      
    capacidad_minima INT NOT NULL CHECK (capacidad_minima <= capacidad_maxima),
    capacidad_maxima INT NOT NULL,                       
    ubicacion VARCHAR(50) NOT NULL,                      
    restaurante_id BIGINT NOT NULL,                      
    FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);

-- Tabla de clientes
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                
    nombre VARCHAR(255) NOT NULL,                        
    telefono VARCHAR(15) NOT NULL UNIQUE                 
);

-- Tabla de reservas
CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                
    fecha_hora_reserva DATETIME NOT NULL,                
    numero_personas INT NOT NULL,                        
    costo DOUBLE NOT NULL,                               
    estado VARCHAR(50) NOT NULL DEFAULT 'activa',        
    multa DOUBLE DEFAULT NULL,                           
    cliente_id BIGINT NOT NULL,                          
    mesa_id BIGINT NOT NULL,                             
    restaurante_id BIGINT NOT NULL,                      
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),    
    FOREIGN KEY (mesa_id) REFERENCES mesas(id),          
    FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);

-- Insertar restaurante de prueba
INSERT INTO restaurantes (nombre, direccion, telefono, hora_apertura, hora_cierre)
VALUES ('Restaurante Gourmet', 'Avenida de la Gastronomía 123', '555-1234', '12:00:00', '22:00:00');

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, telefono)
VALUES 
    ('Juan Pérez', '555-1234'),
    ('María Gómez', '555-5678'),
    ('Carlos Rodríguez', '555-8765'),
    ('Ana Martínez', '555-4321'),
    ('Luis Fernández', '555-1111'),
    ('Laura López', '555-2222'),
    ('Pedro Sánchez', '555-3333'),
    ('Lucía Torres', '555-4444'),
    ('José Morales', '555-5555'),
    ('Sofía Díaz', '555-6666'),
    ('Jorge Jiménez', '555-7777'),
    ('Elena Cruz', '555-8888'),
    ('David Ruiz', '555-9999'),
    ('Claudia Romero', '555-0000'),
    ('Fernando Vargas', '555-1235'),
    ('Carla Mendoza', '555-1236'),
    ('Alberto Castillo', '555-1237'),
    ('Mónica Herrera', '555-1238'),
    ('Patricia Ramírez', '555-1239'),
    ('Ricardo Ortega', '555-1240');


DELIMITER //

CREATE TABLE mesas_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operacion VARCHAR(50),
    detalle JSON,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER before_insert_mesas
BEFORE INSERT ON mesas
FOR EACH ROW
BEGIN
    -- Extraer las tres primeras letras de la ubicación y convertir a mayúsculas
    DECLARE ubicacion_codigo VARCHAR(3);
    SET ubicacion_codigo = UPPER(LEFT(NEW.ubicacion, 3));

    -- Generar el código de mesa automáticamente
    SET NEW.codigo_mesa = CONCAT(
        ubicacion_codigo, '-', 
        NEW.capacidad_maxima, '-', 
        LPAD(IFNULL(
            (SELECT COUNT(*) + 1 
             FROM mesas 
             WHERE LEFT(codigo_mesa, 3) = ubicacion_codigo), 1), 3, '0')
    );

    -- Registrar la operación en la tabla de log
    INSERT INTO mesas_log (operacion, detalle)
    VALUES (
        'INSERT',
        JSON_OBJECT(
            'codigoMesa', NEW.codigo_mesa,
            'ubicacion', NEW.ubicacion,
            'capacidadMaxima', NEW.capacidad_maxima
        )
    );
END;
//

CREATE TRIGGER after_insert_mesas
AFTER INSERT ON mesas
FOR EACH ROW
BEGIN
    -- Borrar el registro correspondiente en mesas_log basado en el código de mesa
    DELETE FROM mesas_log 
    WHERE JSON_UNQUOTE(JSON_EXTRACT(detalle, '$.codigoMesa')) = NEW.codigo_mesa;
END;
//
DELIMITER ;

