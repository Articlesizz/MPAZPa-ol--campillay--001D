# Diagrama de Arquitectura de Microservicios Backend Spring Boot

## Descripción
Estructura en capas del backend Java Spring Boot y su integración con PostgreSQL en Supabase.

```mermaid
graph TD
    subgraph REST_API ["Capa de Exposición (Controller)"]
        EC["EquipoController\n- GET /api/equipos\n- POST /api/equipos\n- PUT /api/equipos/{id}"]
        SC["SolicitudController\n- GET /api/solicitudes\n- POST /api/solicitudes\n- PATCH /api/solicitudes/{id}/estado"]
    end

    subgraph SERVICE ["Capa de Negocio (Service)"]
        ES["EquipoService\n- Lógica de Stock\n- Validación de Códigos"]
        SS["SolicitudService\n- Regla de Anticipación\n- Cambio de Estados"]
    end

    subgraph REPOSITORY ["Capa de Persistencia (Repository JPA)"]
        ER["EquipoRepository\n(JpaRepository)"]
        SR["SolicitudRepository\n(JpaRepository)"]
    end

    subgraph DATABASE ["Infraestructura Cloud (Supabase)"]
        DB[("PostgreSQL Database (Supabase Cloud)\nTables: equipos, solicitudes, usuarios")]
    end

    EC --> ES
    SC --> SS
    ES --> ER
    SS --> SR
    ER -- "Spring Data JPA / HikariCP" --> DB
    SR -- "Spring Data JPA / HikariCP" --> DB
```

## Pruebas Unitarias Backend (JUnit 5 + Mockito)
Se incluyen pruebas unitarias en `EquipoServiceTest` y `SolicitudServiceTest` para verificar el correcto funcionamiento de los métodos de servicio de forma aislada.
