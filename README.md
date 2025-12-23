# Sistema de Gestión de Citas Médicas (SGCM)

## Descripción general
El **Sistema de Gestión de Citas Médicas (SGCM)** es una aplicación backend desarrollada en **Spring Boot**, orientada a la gestión de pacientes, médicos y citas médicas dentro de un consultorio.  
El sistema expone servicios REST que permiten registrar pacientes, médicos y agendar citas médicas, siguiendo una arquitectura en capas basada en **Modelo – Repositorio – Servicio – Controlador**.

Este proyecto corresponde a la **Tarea T02.03 – Construcción de Aplicación de Software**, de la carrera de **Ingeniería de Software – Universidad Politécnica Salesiana**.

---

## Alcance del sistema
El sistema **incluye**:
- Registro de pacientes
- Registro y consulta de médicos
- Agendamiento de citas médicas
- Consulta de historial de citas por paciente
- Exposición de servicios REST documentados

El sistema **no incluye**:
- Módulo de facturación
- Gestión de inventarios
- Autenticación y autorización avanzada

---

## Arquitectura
El SGCM está desarrollado bajo una **arquitectura cliente-servidor en capas**, siguiendo el patrón:

**Modelo → Repositorio → Servicio → Controlador**

### Capas del sistema
- **Modelo (model):** Entidades del dominio (Paciente, Médico, Cita)
- **Repositorio (repository):** Acceso a datos mediante Spring Data JPA
- **Servicio (service):** Lógica de negocio
- **Controlador (controller):** Exposición de endpoints REST
- **Base de Datos:** Configurable mediante JPA (PostgreSQL u otra)

---

## Estructura del proyecto
## Estructura del proyecto

```text
src/main/java/
└── ec/edu/ups/consultoriomedico/
    ├── controller/
    │   ├── CitaController.java
    │   ├── ConsultaController.java
    │   ├── MedicoController.java
    │   └── PacienteController.java
    │
    ├── service/
    │   ├── CitaService.java
    │   ├── ConsultaService.java
    │   ├── MedicoService.java
    │   └── PacienteService.java
    │
    ├── repository/
    │   ├── CitaRepository.java
    │   ├── ConsultaRepository.java
    │   ├── MedicoRepository.java
    │   └── PacienteRepository.java
    │
    ├── model/
    │   ├── Cita.java
        ├── EstadoCita.java
        ├── Usuario.java
    │   ├── Consulta.java
    │   ├── Medico.java
    │   └── Paciente.java
    │
    └── ConsultorioMedicoApplication.java
```

---

## Endpoints implementados

### Pacientes (`/pacientes`)
- `POST /pacientes`  
  Registrar un nuevo paciente.

- `GET /pacientes/{cedula}`  
  Consultar un paciente por número de cédula.

---

### Médicos (`/medicos`)
- `POST /medicos`  
  Registrar un nuevo médico.

- `GET /medicos`  
  Listar todos los médicos registrados.

- `GET /medicos/{id}`  
  Consultar un médico por su identificador.

---

### Citas (`/citas`)
- `POST /citas/agendar`  
  Agendar una cita médica entre un paciente y un médico.

- `GET /citas`  
  Listar todas las citas registradas en el sistema.

- `GET /citas/medico/{idMedico}`  
  Listar las citas asignadas a un médico específico.

- `GET /citas/paciente/{cedula}`  
  Listar las citas de un paciente por número de cédula.

- `PUT /citas/cancelar/{idCita}`  
  Cancelar una cita médica cambiando su estado a **CANCELADA**.

---

### Consultas médicas (`/consultas`)
- `POST /consultas/registrar`  
  Registrar una consulta médica asociada a una cita existente.  
  *(La cita pasa automáticamente al estado **FINALIZADA**).*

- `GET /consultas/cita/{idCita}`  
  Obtener la consulta médica asociada a una cita específica (historia clínica básica).

---

## Tecnologías utilizadas
- **Backend:** Spring Boot  
- **Lenguaje:** Java  
- **Persistencia:** Spring Data JPA / Hibernate  
- **Gestión de dependencias:** Maven  
- **Arquitectura:** REST  
- **Control de versiones:** Git y GitHub  

---

## Ejecución del proyecto
1. Clonar el repositorio:
```bash
git clone https://github.com/usuario/sistema-gestion-citas-medicas.git
```
2. Configurar la base de datos en application.properties.
3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```
---
## Conclusiones

El Sistema de Gestión de Citas Médicas permite aplicar de manera práctica los conceptos de Ingeniería de Software, integrando análisis, diseño y construcción de un backend funcional en Spring Boot.
La arquitectura en capas facilita la mantenibilidad del sistema y permite su futura ampliación con nuevos módulos y un frontend web.

