# 📱 MPAZ Pañol — Sistema Móvil de Préstamos e Inventario Tecnológico

**Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)  
**Sección:** 010D  
**Evaluación Parcial 2 (EP2)** — Escuela de Informática y Telecomunicaciones  
**Institución:** Duoc UC  

---

## 👥 Datos de la Entrega
* **Estudiante Integrante:** Emilio Campillay Soliz
* **Docente:** Viviana Soledad Poblete López
* **Sección:** 010D
* **Asignatura:** Desarrollo de Aplicaciones Móviles

---

## 📄 Descripción del Proyecto
**MPAZ Pañol** es una solución móvil nativa en Android desarrollada para resolver la problemática de gestión, trazabilidad y reserva de recursos tecnológicos (notebooks, kits de robótica, proyectores y equipamiento de red) en el pañol escolar/institucional.

La aplicación permite la interacción entre dos perfiles principales:
1. **Docente / Solicitante:** Explora el catálogo de equipos, arma un borrador de solicitud (carrito), envía solicitudes respetando la **Regla Crítica de 3 Horas de Anticipación** y realiza el seguimiento del estado de sus tickets (*Pendiente, Aceptada, Rechazada, Entregada, Devuelta*).
2. **Encargado de TI / Administrador:** Audita solicitudes entrantes, aprueba o rechaza préstamos con **justificación escrita obligatoria**, gestiona el inventario con ubicación física interna ("Estante A-2", "Rack Redes") y añade nuevos equipos.

---

## 🛠️ Tecnologías e Implementación Técnica

| Componente | Tecnología Utilizada |
| :--- | :--- |
| **Lenguaje** | Kotlin 2.x |
| **Framework UI** | Jetpack Compose (Material Design 3) |
| **Arquitectura** | Model-View-ViewModel (MVVM) |
| **Persistencia Local** | Room Framework (Base de Datos SQLite) |
| **Navegación** | Navigation Compose (`NavHost` / `rememberNavController`) |
| **Animaciones** | `AnimatedVisibility`, `animateColorAsState`, `animateContentSize()` |
| **Recursos Nativos** | Batería (`BatteryManager`), Vibración (`Vibrator`), Linterna (`CameraManager`) |

---

## 📱 Recursos Nativos del Dispositivo Implementados

De acuerdo a la **Práctica 5** y los requerimientos del encargo, se integraron los siguientes recursos nativos:

1. **🔋 Batería (`BatteryManager`):** Monitoreo en tiempo real del porcentaje de batería del dispositivo administrativo. Vinculado al requerimiento del caso 3.3 ("preparación, carga de baterías y revisión previa de recursos por el encargado de TI").
2. **📳 Vibración (`Vibrator` / `VibratorManager`):** Invocación del servicio nativo de vibración con corrutinas y feedback háptico al confirmar login, enviar solicitudes, aprobar o rechazar préstamos.
3. **🔦 Linterna (`CameraManager`):** Control nativo del flash de la cámara para inspección física de equipos en estantes oscuros o Racks del Pañol.

---

## 🔑 Credenciales de Prueba (Base de Datos Precargada)

La base de datos SQLite Room (`mpaz_panol_database.db`) viene precargada automáticamente con usuarios y solicitudes de demostración:

### 🧑‍🏫 Perfil Docente
* **Correo:** `m.lopez@escuela.cl`
* **Contraseña:** `123`

### 🛠️ Perfil Encargado de TI (Administrador)
* **Correo:** `admin@escuela.cl`
* **Contraseña:** `123`

---

## 🎨 Estructura del Proyecto y Packages

```text
com.example.myappnavigation
├── data
│   ├── database       # AppDatabase.kt (Definición SQLite Room y callback de precarga)
│   ├── local
│   │   ├── dao        # UsuarioDao, EquipoDao, SolicitudDao (Consultas SQL)
│   │   └── entity     # UsuarioEntity, EquipoEntity, SolicitudEntity (Tablas SQLite)
│   └── NativeResources.kt # Batería, Vibración y Linterna nativa
├── model              # LoginModel, RegisterModel (Modelos de estado)
├── ui
│   ├── components     # Header.kt, Footer.kt (Componentes UI reutilizables)
│   ├── navigation     # AppNavigation.kt (Enrutamiento centralizado)
│   ├── screen         # Login, Register, CatalogoScreen, CarritoScreen, MisSolicitudesScreen, ConsolaAdminScreen, InventarioAdminScreen
│   ├── theme          # Color.kt, Theme.kt, Type.kt (Paleta M3)
│   └── viewmodel      # LoginViewModel, RegisterViewModel, CatalogoViewModel, SolicitudViewModel
└── MainActivity.kt    # Punto de entrada principal
```

---

## 🚀 Instrucciones para Ejecutar el Proyecto

1. **Requisitos Previos:**
   * Android Studio Ladybug / Jellyfish o superior.
   * Android SDK Min SDK 26 / Target SDK 34+.
   * Emulador de Android o Dispositivo Físico con depuración USB activada.

2. **Pasos:**
   1. Clonar el repositorio:
      ```bash
      git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
      ```
   2. Abrir Android Studio y seleccionar **Open** -> Seleccionar la carpeta del proyecto `MyAppNavigation`.
   3. Esperar que Gradle sincronice las dependencias (`Sync Project with Gradle Files`).
   4. Seleccionar un emulador Android (API 26+) y hacer clic en **Run 'app'** (`Shift + F10`).
