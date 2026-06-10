# GarageLog — Contexto del Proyecto

## Stack
- **Java 17** + **Spring Boot 4.0.6** + **Maven**
- **MySQL 8** en VM VirtualBox (Ubuntu)
- JPA / Hibernate 7 + Jackson

## Estructura del proyecto
```
com.garagelog
├── GarageLogApplication.java         → @SpringBootApplication
├── entity/                           → JPA Entities (5)
├── repository/                       → Spring Data JPA (5)
├── service/                          → Lógica de negocio (4)
├── controller/                       → REST endpoints (4)
└── dto/                              → Request DTOs (2)
```

## Base de datos: `garagelog`

### Tablas

**modelo_vehiculo**
| Columna | Tipo | PK |
|---------|------|----|
| id_modelo | INT AUTO_INCREMENT | PK |
| marca | VARCHAR(50) | |
| modelo | VARCHAR(50) | |
| año | INT | |
| combustible | VARCHAR(30) | |
| ruta_imagen | VARCHAR(255) | |

**vehiculo**
| Columna | Tipo | FK |
|---------|------|----|
| id_vehiculo | INT AUTO_INCREMENT | PK |
| id_modelo | INT | FK → modelo_vehiculo(id_modelo) CASCADE |
| matricula | VARCHAR(20) | |
| km_iniciales | INT | |
| fecha_compra | DATE | |
| notas | TEXT | |

**mantenimiento**
| Columna | Tipo | FK |
|---------|------|----|
| id_mantenimiento | INT AUTO_INCREMENT | PK |
| id_vehiculo | INT | FK → vehiculo(id_vehiculo) CASCADE |
| fecha | DATE | |
| km | INT | |
| coste_total | DECIMAL(10,2) | |
| observaciones | TEXT | |

**pieza**
| Columna | Tipo | PK |
|---------|------|----|
| id_pieza | INT AUTO_INCREMENT | PK |
| nombre | VARCHAR(100) | |
| descripcion | TEXT | |
| precio | DECIMAL(10,2) | |

**mantenimiento_pieza** (N:M con atributos)
| Columna | Tipo | FK |
|---------|------|----|
| id_mantenimiento_pieza | INT AUTO_INCREMENT | PK |
| id_mantenimiento | INT | FK → mantenimiento(id_mantenimiento) CASCADE |
| id_pieza | INT | FK → pieza(id_pieza) RESTRICT |
| cantidad | INT | |
| precio_unitario | DECIMAL(10,2) | |
| observaciones | TEXT | |

### Datos de prueba iniciales
- **modelo_vehiculo**: 1=BMW 320d, 2=Audi A3
- **vehiculo**: 1=1234ABC (BMW), 2=5678DEF (Audi)
- **pieza**: 1=Filtro aceite, 2=Aceite motor, 3=Pastillas freno
- **mantenimiento**: 1=Cambio aceite BMW, 2=Revisión Audi
- **mantenimiento_pieza**: 3 registros relacionando mantenimientos con piezas

## API REST (base: `http://localhost:8080/api`)

### `/api/modelos`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/modelos` | Todos los modelos |
| GET | `/api/modelos/{id}` | Un modelo con vehículos |
| POST | `/api/modelos` | Crear modelo |
| PUT | `/api/modelos/{id}` | Actualizar modelo |
| DELETE | `/api/modelos/{id}` | Eliminar modelo |

### `/api/vehiculos`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/vehiculos` | Todos los vehículos |
| GET | `/api/vehiculos/{id}` | Un vehículo con mantenimientos |
| POST | `/api/vehiculos` | Crear vehículo |
| PUT | `/api/vehiculos/{id}` | Actualizar vehículo |
| DELETE | `/api/vehiculos/{id}` | Eliminar vehículo |

**POST/PUT `/api/vehiculos` body:**
```json
{
  "idModelo": 1,
  "matricula": "ABC123",
  "kmIniciales": 50000,
  "fechaCompra": "2024-01-15",
  "notas": "opcional"
}
```

### `/api/piezas`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/piezas` | Todas las piezas |
| GET | `/api/piezas/{id}` | Una pieza |
| POST | `/api/piezas` | Crear pieza |
| PUT | `/api/piezas/{id}` | Actualizar pieza |
| DELETE | `/api/piezas/{id}` | Eliminar pieza |

### `/api/mantenimientos`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/mantenimientos` | Todos los mantenimientos |
| GET | `/api/mantenimientos/{id}` | Un mantenimiento con piezas |
| GET | `/api/mantenimientos/vehiculo/{idVehiculo}` | Mantenimientos de un vehículo |
| POST | `/api/mantenimientos` | Crear mantenimiento con piezas |
| DELETE | `/api/mantenimientos/{id}` | Eliminar mantenimiento |

**POST `/api/mantenimientos` body:**
```json
{
  "idVehiculo": 1,
  "fecha": "2025-06-01",
  "km": 130000,
  "costeTotal": 150.00,
  "observaciones": "opcional",
  "piezas": [
    {
      "idPieza": 1,
      "cantidad": 1,
      "precioUnitario": 15.00,
      "observaciones": "opcional"
    }
  ]
}
```

## Relaciones JPA
```
ModeloVehiculo 1──N Vehiculo 1──N Mantenimiento 1──N MantenimientoPieza N──1 Pieza
```

- `CASCADE` en borrado: ModeloVehiculo→Vehiculo→Mantenimiento→MantenimientoPieza
- `RESTRICT` en MantenimientoPieza→Pieza (no se borra pieza si está usada)
- Jackson usa `@JsonIgnoreProperties` en ambas direcciones para evitar recursión infinita
- `spring.jpa.open-in-view=true` (por defecto) para lazy loading en serialización

## Cómo arrancar
```powershell
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\mvnw.cmd spring-boot:run
```
