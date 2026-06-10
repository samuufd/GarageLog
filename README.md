# GarageLog

Sistema de gestión de talleres mecánicos compuesto por tres aplicaciones:

| Proyecto | Stack | Descripción |
|----------|-------|-------------|
| `backend/` | Java 17, Spring Boot 4.0.6, Maven, MySQL 8 | API REST |
| `garage_log/` | Flutter 3.35, Dart ^3.9.2 | App móvil Android/iOS |
| `garagelog-desktop/` | Java 17, JavaFX 21, Maven | Aplicación de escritorio |

---

## Requisitos comunes

- **Git**
- **Java 17+** (JDK)
- **Maven** (o usar `mvnw.cmd` incluido en cada proyecto)

## 1. Backend (`backend/`)

API REST que expone endpoints para gestionar vehículos, modelos, mantenimientos y piezas.

### Requisitos
- MySQL 8 corriendo en `localhost:3306`
- Base de datos `garagelog` creada
- Usuario `garageadmin` con contraseña `abcd1234.` (configurable en `application.properties`)

### Ejecutar
```bash
cd backend
mvnw spring-boot:run
```
La API arranca en `http://localhost:8080`. Los endpoints están bajo `/api/`.

### Endpoints principales
- `GET /api/modelos` — listar modelos de vehículo
- `GET /api/vehiculos` — listar vehículos
- `GET /api/vehiculos/search?q=` — buscar vehículos
- `GET /api/vehiculos/{id}/mantenimientos` — mantenimientos de un vehículo
- `GET /api/imagenes/{filename}` — servir imágenes subidas

### Base de datos
Esquema con 5 tablas: `modelo_vehiculo`, `vehiculo`, `mantenimiento`, `pieza`, `mantenimiento_pieza`.
Ver [`GARAGELOG-CONTEXTO.md`](GARAGELOG-CONTEXTO.md) para el detalle de columnas y relaciones.

---

## 2. App Móvil (`garage_log/`)

App Flutter que se conecta al backend.

### Requisitos
- Flutter SDK 3.35+ ([descargar](https://flutter.dev))
- Editor: Android Studio, VS Code o IntelliJ con plugins Dart/Flutter
- Android SDK (para compilar Android)
- Backend accesible desde el dispositivo/emulador

### Ejecutar
```bash
cd garage_log
flutter pub get
flutter run
```

### Configurar IP del servidor
Por defecto apunta a `192.168.1.19:8080`. Para cambiarla:
- Abre el menú lateral → *Configurar servidor*
- También se solicita la primera vez que se inicia la app
- Si usas emulador Android con backend en localhost, usa `10.0.2.2`

### Assets
- `assets/loading.gif` — placeholder animado para imágenes
- `assets/no-image.jpg` — fallback cuando no hay imagen
- `assets/images/logo.png` — logo del taller

---

## 3. Escritorio (`garagelog-desktop/`)

Aplicación JavaFX con interfaz gráfica para gestión completa del taller.

### Requisitos
- JavaFX 21 SDK (los `jmods` están incluidos en `javafx-jmods-21.0.2/`)
- Backend corriendo en `http://localhost:8080`

### Ejecutar
```bash
cd garagelog-desktop
mvnw javafx:run
```

### Funcionalidades
- CRUD de modelos, vehículos, mantenimientos y piezas
- Búsqueda y filtrado
- Visualización de imágenes de vehículos
- Ayuda integrada (F1)

---

## Estructura del repositorio

```
GarageLog/
├── backend/                     # API REST Spring Boot
│   ├── src/main/java/com/garagelog/
│   │   ├── controller/          # Controladores REST
│   │   ├── service/             # Lógica de negocio
│   │   ├── repository/          # Repositorios JPA
│   │   ├── entity/              # Entidades JPA
│   │   └── dto/                 # DTOs de entrada
│   └── src/main/resources/      # application.properties
├── garage_log/                  # App Flutter
│   └── lib/
│       ├── core/                # Tema, widgets compartidos
│       ├── models/              # Modelos Dart
│       ├── providers/           # Provider + ThemeProvider
│       ├── routes/              # Rutas de navegación
│       ├── screens/             # Pantallas (home, listado, detalle, búsqueda)
│       └── services/            # ServerConfig, API client
├── garagelog-desktop/           # App JavaFX
│   └── src/main/java/org/garagelog/desktop/
│       ├── api/                 # Cliente HTTP (Gson)
│       ├── controller/          # Controladores FXML
│       └── model/               # Modelos Java
├── GARAGELOG-CONTEXTO.md        # Documentación técnica detallada
└── README.md                    # Este archivo
```
