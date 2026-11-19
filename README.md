# 📱 MPAZ Pañol — Sistema Móvil de Préstamos e Inventario Tecnológico

**Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)  
**Sección:** 010D  
**Examen Transversal (ET) / Experiencia 3** — Escuela de Informática y Telecomunicaciones  
**Institución:** Duoc UC  

---

## 👥 Datos de la Entrega
* **Estudiante:** Emilio Campillay Soliz
* **Docente:** Viviana Soledad Poblete López
* **Sección:** 010D

### 🔗 Repositorios del proyecto
| Repositorio | Enlace |
| :--- | :--- |
| Frontend Android (este repositorio) | https://github.com/Articlesizz/MPAZPa-ol--campillay--001D |
| Backend Microservicios Spring Boot | https://github.com/Articlesizz/mpaz-panol-backend |

---

## 📄 Descripción del Proyecto
**MPAZ Pañol** es una aplicación Android nativa para gestionar la reserva, préstamo e inventario de recursos tecnológicos (notebooks, kits de robótica, proyectores y equipos de red) del pañol de un establecimiento educacional.

La app trabaja con **dos roles**, cada uno con sus propias pantallas y flujo de navegación:

1. **Docente:** explora el catálogo, arma una solicitud (carrito), la envía respetando la **regla crítica de 3 horas de anticipación** y hace seguimiento de sus tickets (*Pendiente, Aceptada, Rechazada, Entregada, Devuelta*).
2. **Encargado de TI:** revisa las solicitudes entrantes, las aprueba o rechaza con **justificación obligatoria** y gestiona el inventario (stock y ubicación física), usando recursos nativos del dispositivo.

---

## ✅ Requerimientos del ET y cómo se cumplen

| Requerimiento | Implementación |
| :--- | :--- |
| Diseño visual estructurado y navegación jerárquica | Jetpack Compose + Material 3. `AppNavigation.kt` con `NavHost`, rutas separadas por rol y `popUpTo` al iniciar/cerrar sesión. |
| Formularios validados con retroalimentación por campo | `model/Validaciones.kt` centraliza las reglas. Login, Registro y Solicitud muestran el error **bajo cada campo** (`isError` + `supportingText`) y revalidan en vivo. Rechazo exige justificación. |
| Gestión de estado y separación lógica / interfaz | Patrón **MVVM**: `LoginViewModel`, `RegisterViewModel`, `CatalogoViewModel`, `SolicitudViewModel`. Las pantallas solo observan estado (`mutableStateOf`) y delegan acciones. |
| Animaciones funcionales | `AnimatedVisibility` (mensajes de error, aviso de feriado con `fadeIn + expandVertically`), `animateColorAsState` (selector de rol, tarjeta de regla de 3 horas), `animateContentSize`. |
| Consumo de API externa | **Nager.Date** (`https://date.nager.at`), API pública de feriados. Se muestra el próximo feriado de Chile en el catálogo del docente (`FeriadoApiService`, `FeriadoRepository`). |
| Recursos nativos | Batería (`BatteryManager`), Linterna (`CameraManager`), Vibración (`Vibrator`/`VibratorManager`) y feedback háptico. |
| Conexión con microservicios propios | **Retrofit + OkHttp + Gson** hacia el backend Spring Boot (`/api/equipos`, `/api/solicitudes`). Repositorios híbridos: si el backend no responde, se usa SQLite (Room). |
| Programación orientada a objetos | Entidades y DTO como `data class`, repositorios con inyección del DAO por constructor, `object` singleton para `RetrofitClient`, `NativeResources` y `Validaciones`, encapsulamiento con `private set` en los ViewModels. |
| Pruebas unitarias | Android: `ReglasNegocioUnitTest`, `ValidacionesUnitTest`, `ApiExternaUnitTest`. Backend: JUnit 5 + Mockito (ver repositorio del backend). |
| APK firmado y documentación | Build `release` firmado con keystore propio (ver sección de firma). Diagramas y documentación en `documents/`. |

---

## 🛠️ Tecnologías

| Componente | Tecnología |
| :--- | :--- |
| Lenguaje | Kotlin |
| UI | Jetpack Compose (Material Design 3) |
| Arquitectura | MVVM |
| Persistencia local | Room (SQLite) |
| Red | Retrofit 2.11, OkHttp (logging interceptor), Gson |
| Navegación | Navigation Compose |
| Backend propio | Spring Boot 3 + PostgreSQL (Supabase) |
| API externa | Nager.Date (feriados públicos) |
| SDK | minSdk 23 · targetSdk 36 |

---

## 📱 Recursos Nativos

1. **🔋 Batería (`BatteryManager`):** el encargado de TI ve el nivel de batería del dispositivo en la pantalla de inventario (revisión previa de recursos).
2. **🔦 Linterna (`CameraManager`):** enciende el flash para inspeccionar equipos en estantes o racks oscuros.
3. **📳 Vibración (`Vibrator` / `VibratorManager`):** confirmación física al activar la linterna; además se usa feedback háptico al iniciar sesión y enviar solicitudes.

Permisos declarados en `AndroidManifest.xml`: `INTERNET`, `ACCESS_NETWORK_STATE`, `VIBRATE`, `CAMERA`.

---

## 🌐 Comunicación con el Backend

* URL base en `data/remote/RetrofitClient.kt`: `http://10.0.2.2:8080/` (el emulador Android apunta así al `localhost` del PC).
* Para probar en un **celular físico**, cambiar la URL por la IP local del PC (ej. `http://192.168.1.10:8080/`) con ambos en la misma red.
* Si el backend está apagado, la app sigue funcionando con los datos de SQLite (modo offline).

---

## 🔑 Credenciales de Prueba

La base SQLite (`mpaz_panol_database.db`) se precarga con usuarios, equipos y solicitudes de demostración:

| Rol | Correo | Contraseña |
| :--- | :--- | :--- |
| 🧑‍🏫 Docente | `m.lopez@escuela.cl` | `Lopez1!` |
| 🛠️ Encargado de TI | `admin@escuela.cl` | `Admin1!` |

> Las contraseñas de ambos roles deben ser alfanuméricas de al menos 6 caracteres e incluir un carácter especial/símbolo (ej. `!@#$`).

---

## 🎨 Estructura del Proyecto

```text
com.example.myappnavigation
├── data
│   ├── database       # AppDatabase (Room + precarga de datos)
│   ├── local
│   │   ├── dao        # UsuarioDao, EquipoDao, SolicitudDao
│   │   └── entity     # UsuarioEntity, EquipoEntity, SolicitudEntity
│   ├── remote
│   │   ├── api        # EquipoApiService, SolicitudApiService, FeriadoApiService
│   │   └── RetrofitClient.kt
│   ├── repository     # EquipoRepository, SolicitudRepository, UsuarioRepository, FeriadoRepository
│   └── NativeResources.kt
├── model              # LoginModel, RegisterModel, Validaciones
├── ui
│   ├── components     # Header, Footer
│   ├── navigation     # AppNavigation (rutas por rol)
│   ├── screen         # Login, Register, Catalogo, Carrito, MisSolicitudes, ConsolaAdmin, InventarioAdmin
│   ├── theme          # Paleta y tipografía M3
│   └── viewmodel      # LoginVM, RegisterVM, CatalogoVM, SolicitudVM
└── MainActivity.kt
```

---

## 🧪 Pruebas Unitarias

```bash
# Windows
.\gradlew.bat testDebugUnitTest
```

* `ReglasNegocioUnitTest`: regla de 3 horas, control de stock del carrito, cambio de rol y filtros.
* `ValidacionesUnitTest`: correo, nombre, contraseña, confirmación, formato de hora y hora de devolución.
* `ApiExternaUnitTest`: mapeo de la respuesta de la API de feriados.

---

## 🔏 APK Firmado y Llave `.jks` (Evaluación Parcial 4)

Siguiendo las instrucciones de entrega de la **Evaluación Parcial 4**, el repositorio incluye el ejecutable firmado y la llave junto a su configuración lista para compilar:

* **APK Firmado listo para instalar:** `release/MPAZ_Panol_Release_Signed.apk`
* **Llave generada (`.jks`):** `app/mpaz_release.jks` (Alias: `mpaz_key`)
* **Configuración de firma:** `keystore.properties` y bloque `signingConfigs` en `app/build.gradle.kts`

Para recompilar el APK firmado desde línea de comandos:
```bash
.\gradlew.bat assembleRelease
```
El binario generado también queda en `app/build/outputs/apk/release/app-release.apk`.

---

## 📚 Documentación Técnica (`documents/`)

* `arquitectura_general.md` — visión general cliente / backend / base de datos.
* `arquitectura_android_roles.md` — MVVM y flujo por rol.
* `arquitectura_microservicios.md` — capas del backend Spring Boot.
* `secuencia_comunicacion.md` — secuencia de llamadas Retrofit → Spring Boot → PostgreSQL.
* `plan_pruebas_unitarias.md` — plan de pruebas.
* `supabase_init.sql` — creación de tablas y datos de prueba en PostgreSQL.

---

## 🚀 Cómo Ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Articlesizz/MPAZPa-ol--campillay--001D.git
   ```
2. Abrir la carpeta `MyAppNavigation` en Android Studio y sincronizar Gradle.
3. (Opcional) Levantar el backend Spring Boot (`.\gradlew.bat bootRun` en el repositorio del backend).
4. Ejecutar la app en un emulador (API 23+) con **Run 'app'**.
