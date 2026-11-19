# Diagrama de Arquitectura Android Orientada a Roles

## Descripción
Flujo de navegación y separación funcional de la aplicación Android según el rol del usuario autenticado (`DOCENTE` vs. `ENCARGADO_TI`).

```mermaid
graph TD
    START["Inicio App Android"] --> LOGIN["LoginScreen\n(Autenticación SQLite Local)"]
    
    LOGIN -- "Login Exitoso: DOCENTE" --> DOC_FLOW
    LOGIN -- "Login Exitoso: ENCARGADO_TI" --> ADMIN_FLOW

    subgraph DOC_FLOW ["Flujo Rol Docente"]
        CATALOGO["CatalogoScreen\n- Filtro por Categoría\n- Búsqueda de Equipos"]
        CARRITO["CarritoScreen\n- Validación de Anticipación (>=3h)\n- Reserva de Equipos"]
        MIS_SOLICITUDES["MisSolicitudesScreen\n- Historial de Tickets\n- Estado de Préstamos"]

        CATALOGO --> CARRITO
        CARRITO --> MIS_SOLICITUDES
    end

    subgraph ADMIN_FLOW ["Flujo Rol Encargado TI"]
        CONSOLA["ConsolaAdminScreen\n- Aprobar Solicitudes\n- Rechazar con Justificación\n- Recurso Nativo: Vibración"]
        INVENTARIO["InventarioAdminScreen\n- Gestión de Stock\n- Ubicación Física en Pañol\n- Recurso Nativo: Linterna & Batería"]

        CONSOLA <--> INVENTARIO
    end

    DOC_FLOW --> REPO["Capa Repository (SQLite + Retrofit)"]
    ADMIN_FLOW --> REPO
```

## Características por Rol:
- **Docente**:
  - Consulta del catálogo de equipos del pañol.
  - Creación de solicitudes de reserva en carrito con validación de horas de anticipación.
  - Consulta de estados de sus tickets (Pendiente, Aceptada, Rechazada, Entregada, Devuelta).
- **Encargado TI**:
  - Gestión de inventario y monitoreo de stock disponible.
  - Aprobación/Rechazo de solicitudes ingresadas por docentes.
  - Uso de recursos nativos (Linterna para revisión física de estantes, Vibrador para feedback táctil en decisiones).
