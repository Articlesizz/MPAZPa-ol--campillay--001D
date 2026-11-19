# Diagrama de Comunicación entre Android, Retrofit, Spring Boot y Supabase PostgreSQL

## Descripción
Flujo completo de interacción extremo a extremo entre el cliente móvil, la capa Retrofit, el backend Spring Boot y la base de datos PostgreSQL alojada en Supabase Cloud.

```mermaid
sequenceDiagram
    autonumber
    actor Docente as Usuario Docente
    participant App as Android UI (Jetpack Compose)
    participant VM as SolicitudViewModel
    participant Repo as SolicitudRepository
    participant Room as Room SQLite (Local DB)
    participant Retrofit as Retrofit Client
    participant Spring as Spring Boot Backend
    participant Supabase as PostgreSQL (Supabase Cloud)

    Docente->>App: Presiona "Confirmar Solicitud"
    App->>VM: enviarSolicitud(...)
    VM->>Repo: crearSolicitud(solicitud)
    Repo->>Retrofit: solicitudApi.crearSolicitud(solicitud)
    Retrofit->>Spring: POST /api/solicitudes (JSON)
    Spring->>Supabase: INSERT INTO solicitudes ... (SQL JDBC)
    Supabase-->>Spring: OK 201 (Id asignado)
    Spring-->>Retrofit: HTTP 201 Created (Solicitud JSON)
    Retrofit-->>Repo: Response<SolicitudEntity> Exitoso
    Repo->>Room: insertSolicitud(solicitudRemota) [Sincronización Local]
    Repo-->>VM: Resultado Éxito
    VM-->>App: Actualizar UI & Notificar Notificación/Vibración
```
