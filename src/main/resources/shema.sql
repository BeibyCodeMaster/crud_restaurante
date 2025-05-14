-- Tabla roles
CREATE TABLE rol (
    id_rol INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,   
    nombre ENUM('Super', 'Administrador', 'Empleado', 'Cliente')
);

-- Tabla usuario
CREATE TABLE usuario (
    id_usuario INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo_electronico VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(100),
    telefono VARCHAR(15),
    clave VARCHAR(255) NOT NULL, -- Considera encriptar las contraseñas
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    fecha_nacimiento DATE,   
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Relación usuario_rol para asignación de roles
CREATE TABLE usuario_rol (
    id_usuario INT UNSIGNED,
    id_rol INT UNSIGNED,
    PRIMARY KEY(id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

-- Tabla cliente (especialización de usuario)
CREATE TABLE cliente (
    id_cliente INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNSIGNED UNIQUE,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla empleado (especialización de usuario)
CREATE TABLE empleado (
    id_empleado INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNSIGNED UNIQUE,   
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla administrador (especialización de usuario)
CREATE TABLE administrador (
    id_administrador INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNSIGNED UNIQUE,   
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla super (especialización de usuario)
CREATE TABLE super (
    id_super INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNSIGNED UNIQUE,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);
