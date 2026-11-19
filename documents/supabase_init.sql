-- =============================================================================
-- SCRIPT DE INICIALIZACIÓN: MPAZ PAÑOL (SUPABASE POSTGRESQL)
-- Proyecto: Sistema Móvil de Préstamos e Inventario Tecnológico (DSY1105)
-- =============================================================================

-- 1. TABLA: EQUIPOS
CREATE TABLE IF NOT EXISTS equipos (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    stock_total INT NOT NULL DEFAULT 0,
    stock_disponible INT NOT NULL DEFAULT 0,
    estado VARCHAR(50) NOT NULL DEFAULT 'Disponible',
    ubicacion_fisica VARCHAR(100) NOT NULL
);

-- 2. TABLA: USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL DEFAULT 'DOCENTE'
);

-- Vista de compatibilidad para consultas a 'users'
CREATE OR REPLACE VIEW users AS SELECT * FROM usuarios;

-- 3. TABLA: SOLICITUDES
CREATE TABLE IF NOT EXISTS solicitudes (
    id SERIAL PRIMARY KEY,
    codigo_ticket VARCHAR(50) UNIQUE NOT NULL,
    docente_nombre VARCHAR(100) NOT NULL,
    docente_email VARCHAR(150) NOT NULL,
    sala_espacio VARCHAR(150) NOT NULL,
    fecha_uso VARCHAR(50) NOT NULL,
    hora_inicio VARCHAR(10) NOT NULL,
    hora_devolucion VARCHAR(10) NOT NULL,
    equipos_resumen TEXT NOT NULL,
    horas_anticipacion INT NOT NULL DEFAULT 5,
    cumple_anticipacion BOOLEAN NOT NULL DEFAULT TRUE,
    estado_ticket VARCHAR(50) NOT NULL DEFAULT 'Pendiente',
    justificacion_rechazo TEXT,
    fecha_creacion VARCHAR(50) NOT NULL
);

-- =============================================================================
-- CARGA DE DATOS INICIALES (DEMO / MOCKUPS)
-- =============================================================================

-- Usuarios Iniciales
INSERT INTO usuarios (name, email, password, rol) VALUES
('María López', 'm.lopez@escuela.cl', 'Lopez1!', 'DOCENTE'),
('Encargado TI', 'admin@escuela.cl', 'Admin1!', 'ENCARGADO_TI')
ON CONFLICT (email) DO NOTHING;

-- Equipos Iniciales
INSERT INTO equipos (codigo, nombre, categoria, stock_total, stock_disponible, estado, ubicacion_fisica) VALUES
('ROB-001', 'Kit LEGO WeDo 2.0', 'Robótica', 5, 3, 'Disponible', 'Estante A-2'),
('NOT-015', 'Notebook Lenovo V15', 'Notebooks', 12, 8, 'Disponible', 'Armario B-1'),
('PRO-003', 'Proyector Epson X49', 'Proyectores', 3, 2, 'Disponible', 'Estante A-3'),
('RED-004', 'Router TP-Link AX23', 'Redes', 4, 2, 'Disponible', 'Rack Principal Redes')
ON CONFLICT (codigo) DO NOTHING;

-- Solicitudes Iniciales
INSERT INTO solicitudes (codigo_ticket, docente_nombre, docente_email, sala_espacio, fecha_uso, hora_inicio, hora_devolucion, equipos_resumen, horas_anticipacion, cumple_anticipacion, estado_ticket, justificacion_rechazo, fecha_creacion) VALUES
('MP-0268', 'María López', 'm.lopez@escuela.cl', 'Laboratorio de Computación 1', '21 oct. 2026', '14:00', '16:00', '1 × Kit LEGO WeDo 2.0, 1 × Notebook Lenovo V15', 5, true, 'Pendiente', NULL, '21 oct. 2026 - 09:00'),
('MP-0261', 'María López', 'm.lopez@escuela.cl', 'Sala 4', '22 oct. 2026', '10:00', '12:00', '1 × Proyector Epson X49', 5, true, 'Aceptada', NULL, '20 oct. 2026 - 15:30'),
('MP-0254', 'María López', 'm.lopez@escuela.cl', 'Laboratorio de Computación 1', '20 oct. 2026', '11:00', '13:00', '2 × Router TP-Link AX23', 5, true, 'Rechazada', 'Equipos reservados para mantenimiento de la red durante ese horario.', '19 oct. 2026 - 08:00'),
('MP-0258', 'María López', 'm.lopez@escuela.cl', 'Sala 2', '21 oct. 2026', '08:00', '12:00', '1 × Notebook Lenovo V15', 5, true, 'Entregada', NULL, '20 oct. 2026 - 17:00'),
('MP-0246', 'María López', 'm.lopez@escuela.cl', 'Sala 3', '19 oct. 2026', '09:00', '11:00', '1 × Kit LEGO WeDo 2.0', 5, true, 'Devuelta', NULL, '18 oct. 2026 - 12:00')
ON CONFLICT (codigo_ticket) DO NOTHING;
