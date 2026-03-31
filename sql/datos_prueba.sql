-- USUARIOS
-- password para todos: Otec2024$
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES
('Administrador', 'Primavera', 'admin@otec.cl', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN'),
('Carlos', 'Mendoza', 'carlos.mendoza@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ESTUDIANTE'),
('Ana', 'Torres', 'ana.torres@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ESTUDIANTE'),
('Diego', 'Ramírez', 'diego.ramirez@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ESTUDIANTE');

-- PROGRAMAS
INSERT INTO programas (nombre, descripcion, duracion_horas, tecnologia, modalidad, nivel, estado) VALUES
('Desarrollo FullStack JavaScript', 'Bootcamp intensivo de desarrollo web con JavaScript', 200, 'JavaScript', 'ONLINE', 'INTERMEDIO', 'ACTIVO'),
('Desarrollo FullStack Java', 'Bootcamp de desarrollo backend y frontend con Java', 240, 'Java', 'ONLINE', 'INTERMEDIO', 'ACTIVO'),
('Automatización de Pruebas', 'Curso de testing con Selenium y Cypress', 120, 'Testing', 'HIBRIDO', 'BASICO', 'ACTIVO'),
('Análisis de Datos', 'Curso de análisis de datos con Python y Power BI', 160, 'Python', 'ONLINE', 'BASICO', 'ACTIVO');

-- MÓDULOS JAVASCRIPT
INSERT INTO modulos (nombre, descripcion, orden, programa_id) VALUES
('Fundamentos de JavaScript', 'Variables, tipos de datos, funciones y estructuras de control', 1, 1),
('DOM y Eventos', 'Manipulación del DOM, eventos y formularios', 2, 1),
('Programación Asíncrona', 'Callbacks, Promesas y Async/Await', 3, 1),
('Node.js y Express', 'Servidor backend con Node.js y Express', 4, 1),
('Base de Datos con MySQL', 'Conexión y consultas con MySQL desde Node.js', 5, 1),
('React Fundamentos', 'Componentes, props, estado y hooks', 6, 1),
('React Avanzado', 'Context API, React Router y consumo de APIs', 7, 1),
('Proyecto Integrador', 'Desarrollo de aplicación fullstack completa', 8, 1);

-- MÓDULOS JAVA
INSERT INTO modulos (nombre, descripcion, orden, programa_id) VALUES
('Fundamentos de Java', 'Sintaxis, tipos de datos y estructuras de control', 1, 2),
('Programación Orientada a Objetos', 'Clases, herencia, polimorfismo e interfaces', 2, 2),
('Spring Boot', 'Desarrollo de aplicaciones con Spring Boot', 3, 2),
('Spring Data JPA', 'Persistencia de datos con JPA e Hibernate', 4, 2),
('Spring Security', 'Autenticación y autorización con Spring Security', 5, 2),
('Thymeleaf', 'Desarrollo de vistas dinámicas con Thymeleaf', 6, 2),
('APIs REST', 'Diseño y desarrollo de APIs RESTful', 7, 2),
('Proyecto Integrador', 'Desarrollo de aplicación fullstack completa', 8, 2);

-- MÓDULOS TESTING
INSERT INTO modulos (nombre, descripcion, orden, programa_id) VALUES
('Fundamentos de Testing', 'Conceptos básicos de calidad y pruebas de software', 1, 3),
('Selenium WebDriver', 'Automatización de pruebas web con Selenium', 2, 3),
('Cypress', 'Pruebas end-to-end con Cypress', 3, 3),
('Integración Continua', 'CI/CD con GitHub Actions', 4, 3);

-- MÓDULOS ANÁLISIS DE DATOS
INSERT INTO modulos (nombre, descripcion, orden, programa_id) VALUES
('Fundamentos de Python', 'Sintaxis, estructuras de datos y funciones', 1, 4),
('Pandas y NumPy', 'Manipulación y análisis de datos con Pandas', 2, 4),
('Visualización de Datos', 'Gráficos con Matplotlib y Seaborn', 3, 4),
('Power BI', 'Dashboards y reportes con Power BI', 4, 4),
('Proyecto de Análisis', 'Proyecto final de análisis de datos real', 5, 4);

-- MATRÍCULAS
INSERT INTO matriculas (usuario_id, programa_id, fecha_inicio, estado) VALUES
(2, 1, '2026-01-15', 'EN_CURSO'),
(3, 2, '2026-02-01', 'EN_CURSO'),
(4, 3, '2026-03-01', 'EN_CURSO');

-- HITOS CARLOS (matricula_id=1, programa JavaScript)
INSERT INTO hitos (matricula_id, modulo_id, tipo, nota, aprobado, fecha, observacion) VALUES
(1, 1, 'EVALUACION', 6.0, null, '2026-01-20', 'Excelente manejo de variables'),
(1, 1, 'EVALUACION', 5.5, null, '2026-01-22', 'Buen rendimiento en funciones'),
(1, 1, 'MODULO_COMPLETADO', 5.8, true, '2026-01-23', 'Módulo aprobado con promedio 5.8'),
(1, 2, 'EVALUACION', 5.0, null, '2026-02-01', 'Buen manejo del DOM'),
(1, 2, 'EVALUACION', 4.5, null, '2026-02-03', 'Eventos manejados correctamente'),
(1, 2, 'MODULO_COMPLETADO', 4.8, true, '2026-02-04', 'Módulo aprobado con promedio 4.8'),
(1, 3, 'EVALUACION', 3.5, null, '2026-02-15', 'Dificultades con promesas'),
(1, 3, 'EVALUACION', 3.0, null, '2026-02-17', 'Debe reforzar async/await'),
(1, 3, 'MODULO_COMPLETADO', 3.3, false, '2026-02-18', 'Módulo reprobado con promedio 3.3');

-- HITOS ANA (matricula_id=2, programa Java)
INSERT INTO hitos (matricula_id, modulo_id, tipo, nota, aprobado, fecha, observacion) VALUES
(2, 9, 'EVALUACION', 6.5, null, '2026-02-05', 'Excelente dominio de Java'),
(2, 9, 'EVALUACION', 7.0, null, '2026-02-07', 'Perfecto en estructuras de control'),
(2, 9, 'MODULO_COMPLETADO', 6.8, true, '2026-02-08', 'Módulo aprobado con promedio 6.8'),
(2, 10, 'EVALUACION', 6.0, null, '2026-02-20', 'Buen manejo de herencia'),
(2, 10, 'EVALUACION', 5.5, null, '2026-02-22', 'Polimorfismo bien aplicado'),
(2, 10, 'MODULO_COMPLETADO', 5.8, true, '2026-02-23', 'Módulo aprobado con promedio 5.8');

-- HITOS DIEGO (matricula_id=3, programa Testing)
INSERT INTO hitos (matricula_id, modulo_id, tipo, nota, aprobado, fecha, observacion) VALUES
(3, 17, 'EVALUACION', 5.0, null, '2026-03-05', 'Buen entendimiento del testing'),
(3, 17, 'EVALUACION', 5.5, null, '2026-03-07', 'Casos de prueba bien diseñados'),
(3, 17, 'MODULO_COMPLETADO', 5.3, true, '2026-03-08', 'Módulo aprobado con promedio 5.3');



-- borrar datos de las tablas

USE otec_primavera;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE hitos;
TRUNCATE TABLE matriculas;
TRUNCATE TABLE modulos;
TRUNCATE TABLE programas;
TRUNCATE TABLE usuarios;
SET FOREIGN_KEY_CHECKS = 1;
