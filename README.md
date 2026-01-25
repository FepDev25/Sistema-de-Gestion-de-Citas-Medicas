# Sistema de Gestión de Citas Médicas (SGCM)

## Descripción general

El **Sistema de Gestión de Citas Médicas (SGCM)** es una aplicación backend desarrollada en **Spring Boot**, orientada a la gestión de pacientes, médicos y citas médicas dentro de un consultorio.
El sistema expone servicios REST seguros y optimizados que permiten registrar pacientes, médicos y agendar citas médicas, siguiendo una arquitectura en capas basada en **Modelo – Repositorio – Servicio – Controlador**.

Este proyecto corresponde a la **Tarea T02.03 – Construcción de Aplicación de Software**, de la carrera de **Ingeniería de Software – Universidad Politécnica Salesiana**.

---

## Características y Mejoras

El sistema ha sido refactorizado para incluir **mejores prácticas de arquitectura y seguridad**:

### Seguridad (Security)

- **Autenticación Stateless**: Implementación de **JWT (JSON Web Tokens)** para autenticación segura.
- **Hashing de Contraseñas**: Uso de **BCrypt** para almacenar contraseñas de manera segura.
- **Control de Acceso (RBAC)**: Roles definidos (`PACIENTE`, `MEDICO`) con permisos específicos por endpoint.
- **Protección**: Filtro de seguridad personalizado para validar tokens en cada petición.

### Arquitectura y Calidad de Código

- **DTOs (Data Transfer Objects)**: Separación de la capa de presentación y persistencia. Validación de entrada (`@NotNull`, `@NotBlank`) en DTOs.
- **Mappers**: Uso de **MapStruct** para la conversión automática entre Entidades y DTOs.
- **Manejo de Excepciones**: `GlobalExceptionHandler` (`@RestControllerAdvice`) para capturar errores y devolver respuestas JSON estandarizadas.
- **Transaccionalidad**: Uso de `@Transactional` para garantizar la integridad de datos en operaciones críticas.
- **Optimización de Consultas**: Consultas nativas y JPQL optimizadas para evitar problemas de rendimiento (e.g., validación de solapamiento de citas).

---

## Alcance del sistema

El sistema **incluye**:

- Registro de pacientes y médicos (con contraseñas seguras)
- Agendamiento de citas médicas (con validación de disponibilidad)
- Registro de consultas médicas (solo Médicos)
- Autenticación y Autorización vía JWT
- Exposición de servicios REST documentados

---

## Arquitectura

El SGCM está desarrollado bajo una **arquitectura cliente-servidor en capas**, siguiendo el patrón:

Modelo → Repositorio → Servicio → Controlador

### Capas del sistema

- **Modelo (model):** Entidades del dominio (Paciente, Médico, Cita)
- **DTO (dto):** Objetos de transferencia de datos con validaciones
- **Repositorio (repository):** Acceso a datos mediante Spring Data JPA
- **Servicio (service):** Lógica de negocio y transaccionalidad
- **Controlador (controller):** Exposición de endpoints REST
- **Seguridad (security/config):** Configuración de Spring Security y JWT
- **Mapper (mapper):** Conversión de objetos
- **Excepción (exception):** Manejo de errores

---

## Estructura del proyecto

```text
src/main/java/com/ingenieriasoftware/consultoriomedico/
├── config/             # Configuración de Seguridad
├── controller/         # Controladores REST (AuthController, Cita, etc.)
├── dto/                # Data Transfer Objects (AuthRequest, CitaRequest, etc.)
├── exception/          # Excepciones personalizadas y Handler global
├── mapper/             # Interfaces de MapStruct
├── model/              # Entidades JPA
├── repository/         # Interfaces de Repositorio
├── security/           # Lógica de JWT y UserDetails
├── service/            # Lógica de Negocio
└── ConsultorioMedicoApplication.java
```

---

## Tecnologías utilizadas

- **Backend:** Spring Boot 3+
- **Lenguaje:** Java 21
- **Persistencia:** Spring Data JPA / Hibernate
- **Base de Datos:** PostgreSQL
- **Seguridad:** Spring Security, JJWT (JWT)
- **Mapping:** MapStruct
- **Gestión de dependencias:** Maven
- **Control de versiones:** Git y GitHub

---

## Configuración y Ejecución

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/FepDev25/Sistema-de-Gesti-n-de-Citas-M-dicas.git
   ```

2. **Configurar Base de Datos y JWT**:
   Editar `src/main/resources/application.properties`:

   ```properties
   # DB
   spring.datasource.url=jdbc:postgresql://localhost:5432/sgcm_db
   spring.datasource.username=sgcm_user
   spring.datasource.password=ingsoftware

   # JWT Configuration
   jwt.secret=TU_CLAVE_SECRETA_SUPER_SEGURA
   jwt.expiration=36000000
   ```

3. **Ejecutar la aplicación**:

   ```bash
   ./mvnw spring-boot:run
   ```

---

## Endpoints Principales

### Autenticación (`/auth`)

- `POST /auth/login`: Obtener token JWT enviando `username` y `password`.

### Pacientes (`/pacientes`)

- `POST /pacientes`: Registrar nuevo paciente (Público/Autenticado).
- `GET /pacientes/{cedula}`: Consultar paciente.

### Médicos (`/medicos`)

- `POST /medicos`: Registrar nuevo médico.
- `GET /medicos`: Listar médicos (Público).

### Citas (`/citas`)

- `POST /citas/agendar`: Agendar cita (Requiere rol `PACIENTE` o `MEDICO`).
- `PUT /citas/cancelar/{id}`: Cancelar cita.

### Consultas (`/consultas`)

- `POST /consultas/registrar`: Registrar consulta (Requiere rol `MEDICO`).

---

## Documentación (Swagger UI)

URL: <http://localhost:8080/swagger-ui/index.html>

![Documentacion](resources/image.png)

---

## Pruebas unitarias

El proyecto cuenta con una cobertura de código del **82%** (100% en servicios) validada con **JaCoCo**.

Para ejecutar pruebas:

```bash
./mvnw clean test
```

![Pruebas Unitarias](resources/tests.png)
![Reporte de Cobertura](resources/jacoco.png)
