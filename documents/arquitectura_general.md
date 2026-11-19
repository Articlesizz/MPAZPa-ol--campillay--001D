# Diagrama de Arquitectura General del Sistema

## Descripción
Este documento describe la arquitectura general de la solución desarrollada para la Experiencia 3 / Examen Transversal de la asignatura. La arquitectura sigue el patrón **MVVM** en el cliente Android y una **Arquitectura en Capas (Controller-Service-Repository)** en el backend Spring Boot.

```mermaid
graph TD
    subgraph Client ["Cliente Android (Dispositivo Móvil)"]
        UI["Jetpack Compose UI\n(Login, Catalogo, Carrito, Admin)"]
        VM["ViewModels\n(CatalogoVM, SolicitudVM, LoginVM)"]
        REPO["Repository Layer\n(EquipoRepo, SolicitudRepo, UsuarioRepo)"]
        ROOM[("SQLite Local (Room DB)\nAutenticación & Offline Cache")]
        RETROFIT["Retrofit HTTP Client\n(Gson / OkHttp)"]
        NAT["Recursos Nativos\n(Batería, Linterna, Vibración)"]
    end

    subgraph Cloud ["Backend Cloud (Spring Boot)"]
        SB["Microservicio Spring Boot\n(Port 8080 / Cloud Host)\n- RestControllers\n- Services\n- Repositories JPA"]
    end

    subgraph Database ["Base de Datos Cloud (Supabase)"]
        PG[("PostgreSQL Cloud (Supabase)\n- usuarios\n- equipos\n- solicitudes")]
    end

    UI --> VM
    VM --> REPO
    VM --> NAT
    REPO --> ROOM
    REPO --> RETROFIT
    RETROFIT -- "REST / JSON (HTTP/HTTPS)" --> SB
    SB -- "JDBC / JPA Hibernate" --> PG
```

## Componentes Clave:
1. **Frontend Android**:
   - Desenvolvido en Kotlin con Jetpack Compose.
   - Implementa el patrón **MVVM** y la capa **Repository**.
   - Gestión de autenticación local y sincronización offline en **SQLite** a través de **Room**.
   - Consumo de microservicios REST por medio de **Retrofit**.
   - Acceso a recursos nativos del dispositivo (`BatteryManager`, `Vibrator`, `CameraManager`).
2. **Backend Spring Boot**:
   - Desarrollado en Java con Spring Boot.
   - Arquitectura por capas (`Controller`, `Service`, `Repository`, `Entity`).
   - Pruebas unitarias mediante **JUnit 5** y **Mockito**.
3. **Base de Datos en Supabase (Cloud)**:
   - PostgreSQL relacional hospedado en Supabase Cloud.
