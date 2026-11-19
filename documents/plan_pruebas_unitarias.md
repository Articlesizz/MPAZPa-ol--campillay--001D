# 🧪 Plan y Especificación de Pruebas Unitarias

**Proyecto:** MPAZ Pañol — Sistema Móvil de Préstamos e Inventario Tecnológico  
**Ubicación de los Tests:** `app/src/test/java/com/example/myappnavigation/ReglasNegocioUnitTest.kt`  
**Framework de Pruebas:** JUnit 4  

---

## 📋 Resumen de Cobertura de Pruebas

Se diseñaron e implementaron pruebas unitarias enfocadas en las **reglas de negocio centrales** y la lógica de estado de los ViewModels:

| ID | Módulo / Clase | Escenario de Prueba | Resultado Esperado |
| :--- | :--- | :--- | :--- |
| **TC-01** | `SolicitudViewModel` | Anticipación con horario >= 3 horas (ej. 14:00 vs mock 09:00 = 5h). | `horasAnticipacion = 5`, `cumpleAnticipacion = true`. |
| **TC-02** | `SolicitudViewModel` | Anticipación en el límite exacto de 3 horas (12:00 vs mock 09:00 = 3h). | `horasAnticipacion = 3`, `cumpleAnticipacion = true`. |
| **TC-03** | `SolicitudViewModel` | Anticipación con menos de 3 horas (10:00 vs mock 09:00 = 1h). | `horasAnticipacion = 1`, `cumpleAnticipacion = false`. |
| **TC-04** | `SolicitudViewModel` | Formato de hora inválido o corrupto ("invalido"). | Aplica fallback seguro a las 14:00 (5h) sin crasheos. |
| **TC-05** | `CatalogoViewModel` | Agregar equipo nuevo al carrito. | Carrito con 1 elemento y `cantidad = 1`. |
| **TC-06** | `CatalogoViewModel` | Incrementar cantidad superando el `stockDisponible`. | La cantidad se detiene en el límite del stock disponible. |
| **TC-07** | `CatalogoViewModel` | Decrementar cantidad hasta 0. | El equipo es eliminado automáticamente del carrito. |
| **TC-08** | `CatalogoViewModel` | Limpiar carrito completo (`limpiarCarrito`). | La lista `carritoList` queda con tamaño 0. |
| **TC-09** | `LoginViewModel` | Selección entre perfiles de usuario (DOCENTE vs ENCARGADO_TI). | Actualiza `selectedRol` y preconfigura los correos correspondientes. |
| **TC-10** | `RegisterViewModel` | Validación de contraseñas desiguales. | Permite validar el estado de `password` y `confirmPassword`. |
| **TC-11** | `CatalogoViewModel` | Filtro por categoría y búsqueda textual. | Actualiza reactivamente `selectedCategoria` y `searchQuery`. |

---

## 🚀 Cómo Ejecutar las Pruebas

Desde la raíz del proyecto, ejecutar el comando de Gradle:

```bash
# En Windows (PowerShell / CMD)
.\gradlew.bat testDebugUnitTest

# En Linux / macOS
./gradlew testDebugUnitTest
```

Los reportes HTML generados quedan disponibles en:
`app/build/reports/tests/testDebugUnitTest/index.html`
