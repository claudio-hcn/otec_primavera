# 🌱 OTEC Primavera — Plataforma de Gestión Académica

MVP web para la gestión académica de la OTEC Primavera, desarrollado como proyecto evaluado del Módulo 6 del bootcamp Java Full Stack (Talento Digital / SENCE).

---

## 📋 Descripción

La plataforma centraliza la gestión de programas de capacitación, estudiantes, matrículas y seguimiento de progreso académico. Permite a los administradores gestionar la oferta educativa y registrar hitos de avance, mientras los estudiantes pueden consultar su matrícula y progreso en tiempo real.

---

## 🚀 Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje base |
| Spring Boot | 4.0.5 | Framework principal |
| Spring MVC | — | Patrón MVC y controladores |
| Spring Security | — | Autenticación y control de acceso por roles |
| Spring Data JPA | — | Persistencia de datos |
| Thymeleaf | — | Motor de plantillas para vistas |
| MySQL | 8.x | Base de datos relacional |
| Maven | — | Gestión de dependencias |
| Bootstrap | 5.3 | Estilos y componentes UI |
| Lombok | — | Reducción de código repetitivo |

---

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/otec/primavera/
├── controller/
│   ├── MainController.java
│   ├── AdminController.java
│   ├── EstudianteController.java
│   └── rest/
│       ├── EstudianteRestController.java
│       └── ProgramaRestController.java
├── model/
│   ├── Usuario.java
│   ├── Programa.java
│   ├── Modulo.java
│   ├── Matricula.java
│   └── Hito.java
├── dto/
│   ├── UsuarioRegistroDTO.java
│   ├── UsuarioResponseDTO.java
│   ├── ProgramaDTO.java
│   └── ProgresoDTO.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── ProgramaRepository.java
│   ├── ModuloRepository.java
│   ├── MatriculaRepository.java
│   └── HitoRepository.java
├── service/
│   ├── UsuarioService.java
│   ├── ProgramaService.java
│   ├── MatriculaService.java
│   └── HitoService.java
└── security/
    ├── SecurityConfig.java
    └── UserDetailsServiceImpl.java
```

---

## 🔐 Roles y Accesos

| Rol | Acceso |
|---|---|
| `ADMIN` | Gestión completa de programas, módulos, estudiantes, matrículas, hitos y APIs REST |
| `ESTUDIANTE` | Consulta de su propia matrícula y progreso académico |

---

## ✨ Funcionalidades

### Administrador
- Dashboard con resumen de programas, estudiantes y matrículas
- CRUD de programas de capacitación (con tecnología, modalidad, nivel y estado)
- Gestión de módulos por programa
- Registro de estudiantes con contraseña encriptada (BCrypt)
- Matrícula de estudiantes en programas activos (un programa activo por estudiante)
- Registro de hitos por matrícula:
  - **Evaluación**: registro de notas por módulo
  - **Módulo Completado**: cierre automático con cálculo de promedio y resultado Aprobado/Reprobado (nota mínima 4.0)
  - **Asistencia**: registro de participación
- Validaciones de negocio:
  - No se puede cerrar un módulo sin evaluaciones previas
  - No se pueden agregar evaluaciones a un módulo ya cerrado
  - No se puede matricular un estudiante con programa en curso

### Estudiante
- Portal personal con datos de matrícula activa
- Vista de progreso con barra de avance calculada automáticamente
- Historial de hitos con notas y resultados

### APIs REST
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/estudiantes` | Lista todos los estudiantes |
| GET | `/api/estudiantes/{id}` | Obtiene un estudiante por id |
| GET | `/api/estudiantes/{id}/progreso` | Progreso completo del estudiante en JSON |
| GET | `/api/programas` | Lista todos los programas |
| GET | `/api/programas/{id}` | Obtiene un programa por id |
| POST | `/api/programas` | Crea un programa nuevo |
| DELETE | `/api/programas/{id}` | Elimina un programa |

---

## ⚙️ Instalación y Configuración

### Prerrequisitos
- Java 17
- Maven
- MySQL 8.x
- IDE (VS Code con extensiones Java o IntelliJ IDEA)

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/otec-primavera.git
cd otec-primavera
```

**2. Crear la base de datos**
```sql
CREATE DATABASE otec_primavera;
```

**3. Configurar credenciales**

Edita el archivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/otec_primavera
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

**4. Levantar la aplicación por primera vez**

Esto permite que JPA cree las tablas automáticamente:
```bash
./mvnw spring-boot:run
```

Espera hasta ver:
```
Started OtecPrimaveraApplication in X.XXX seconds
```

Luego detén la aplicación.

**5. Cargar datos de prueba**

Ejecuta el script `sql/datos_prueba.sql` en MySQL Workbench o desde terminal:
```bash
mysql -u root -p otec_primavera < sql/datos_prueba.sql
```

**6. Volver a levantar la aplicación**
```bash
./mvnw spring-boot:run
```

**7. Abrir en el navegador**
```
http://localhost:8080/login
```

---

## 👥 Credenciales de Prueba

| Email | Password | Rol |
|---|---|---|
| admin@otec.cl | Otec2024$ | Administrador |
| carlos.mendoza@gmail.com | Otec2024$ | Estudiante |
| ana.torres@gmail.com | Otec2024$ | Estudiante |
| diego.ramirez@gmail.com | Otec2024$ | Estudiante |

> Los estudiantes de prueba ya tienen matrículas e hitos registrados para demostrar el cálculo de progreso.

---

## 🗄️ Modelo de Datos

```
Usuario ──< Matricula >── Programa ──< Modulo
                │
                └──< Hito >── Modulo
```

- Un **Usuario** puede tener una sola matrícula activa a la vez
- Un **Programa** tiene muchos **Módulos**
- Una **Matrícula** registra muchos **Hitos**
- Un **Hito** referencia un **Módulo** específico

---

## 📁 Script de Datos de Prueba

El archivo `sql/datos_prueba.sql` incluye:
- 1 usuario administrador
- 3 estudiantes
- 4 programas (JavaScript, Java, Testing, Análisis de Datos)
- Módulos para cada programa
- Matrículas activas para los 3 estudiantes
- Hitos con evaluaciones, cierres de módulo y resultados

Para ejecutarlo directamente desde MySQL Workbench: abre el archivo y ejecuta con `Ctrl + Shift + Enter`.

---

## 👤 Autor

Claudio Carrasco Navarrete  
GitHub: [@claudio-hcn](https://github.com/claudio-hcn)  
LinkedIn: [linkedin.com/in/claudio-carrasco-navarrete-55a25181](https://linkedin.com/in/claudio-carrasco-navarrete-55a25181)