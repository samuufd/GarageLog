# Manual de Usuario - GarageLog Desktop

---

## 1. Introducción

GarageLog Desktop es una aplicación de escritorio diseñada para la gestión integral de talleres mecánicos y garajes. Permite administrar vehículos, modelos, piezas y mantenimientos de forma centralizada, facilitando el control y seguimiento de todas las operaciones del taller.

La aplicación funciona conectada a un servidor backend que almacena los datos en una base de datos MySQL, garantizando la persistencia, integridad y seguridad de la información.

### 1.1 Requisitos del sistema

- **Sistema operativo**: Windows 10 o superior
- **Java Runtime**: JRE 17 o superior
- **Servidor backend**: Spring Boot ejecutándose en `http://localhost:8080`
- **Base de datos**: MySQL 8.0 o superior
- **Memoria RAM**: 512 MB mínimo (1 GB recomendado)
- **Resolución de pantalla**: 1024x768 o superior

### 1.2 Requisitos técnicos (para desarrollo)

- **JDK**: 17 o superior
- **Maven**: 3.6 o superior (incluye wrapper `mvnw.cmd`)
- **Backend**: proyecto Spring Boot independiente

---

## 2. Instalación y ejecución

### 2.1 Iniciar el servidor backend

1. Abre una terminal en la carpeta `backend/`
2. Ejecuta:
   ```
   .\mvnw.cmd spring-boot:run
   ```
3. Espera hasta que aparezca el mensaje "Started Application in X.XX seconds"

### 2.2 Iniciar la aplicación de escritorio

1. Abre una terminal en la carpeta raíz del proyecto
2. Ejecuta:
   ```
   .\mvnw.cmd javafx:run
   ```
3. La ventana principal de GarageLog Desktop se abrirá automáticamente

### 2.3 Generar un ejecutable portátil

1. Ejecuta el script de PowerShell incluido:
   ```
   .\build-exe.ps1
   ```
2. El ejecutable se generará en la carpeta `dist/`

---

## 3. Funcionalidades de la aplicación

### 3.1 Pantalla principal

La pantalla principal muestra un listado de todos los vehículos registrados en el sistema. Desde aquí se accede a todas las funcionalidades de la aplicación mediante los botones de la barra inferior.

**Elementos de la pantalla principal:**

| Elemento | Descripción |
|---|---|
| Tabla de vehículos | Muestra ID, Matrícula, Modelo, KM Iniciales y Fecha de Compra |
| Botón Refrescar | Recarga los datos desde el servidor |
| Botón Nuevo Vehículo | Abre el formulario para registrar un nuevo vehículo |
| Botón Editar Vehículo | Abre el formulario para modificar un vehículo existente |
| Botón Ver Mantenimientos | Abre la ventana de mantenimientos del vehículo seleccionado |
| Botón Eliminar Vehículo | Elimina el vehículo seleccionado |
| Botón Gestión Modelos | Abre la ventana de gestión de modelos |
| Botón Gestión Piezas | Abre la ventana de gestión de piezas |
| Botón Ayuda | Abre el sistema de ayuda integrado |

### 3.2 Módulo Vehículos

**Crear un vehículo:**

1. Pulsa **Nuevo Vehículo**
2. Selecciona un **Modelo** del desplegable (debe existir previamente)
3. Introduce la **Matrícula**
4. Introduce los **KM Iniciales** (solo números)
5. Selecciona la **Fecha de Compra**
6. Añade **Notas** (opcional)
7. Pulsa **Guardar**

**Editar un vehículo:**

1. Selecciona el vehículo en la tabla principal
2. Pulsa **Editar Vehículo**
3. Modifica los campos necesarios
4. Pulsa **Guardar**

**Eliminar un vehículo:**

1. Selecciona el vehículo en la tabla
2. Pulsa **Eliminar Vehículo**
3. Confirma la operación en el diálogo

**Nota importante:** Al eliminar un vehículo se eliminan también todos sus mantenimientos asociados.

### 3.3 Módulo Modelos de Vehículo

Permite gestionar el catálogo de modelos (marca, modelo, año, combustible).

**Campos del formulario:**

| Campo | Obligatorio | Descripción |
|---|---|---|
| Marca | Sí | Fabricante del vehículo (ej. Toyota, BMW) |
| Modelo | Sí | Nombre del modelo (ej. Corolla, X5) |
| Año | No | Año de fabricación o lanzamiento |
| Combustible | No | Tipo: Gasolina, Diésel, Híbrido, Eléctrico, GLP |

**Operaciones:**

- **Crear**: pulsar Nuevo, rellenar formulario, Guardar
- **Editar**: seleccionar modelo, pulsar Editar, modificar, Guardar
- **Eliminar**: seleccionar modelo, pulsar Eliminar, confirmar

**Restricción:** No se puede eliminar un modelo que tenga vehículos asociados.

### 3.4 Módulo Piezas

Permite gestionar el catálogo de piezas y recambios.

**Campos del formulario:**

| Campo | Obligatorio | Descripción |
|---|---|---|
| Nombre | Sí | Nombre de la pieza |
| Descripción | No | Información adicional |
| Precio | Sí | Precio unitario |

**Operaciones:**

- **Crear**: pulsar Nuevo, rellenar formulario, Guardar
- **Editar**: seleccionar pieza, pulsar Editar, modificar, Guardar
- **Eliminar**: seleccionar pieza, pulsar Eliminar, confirmar

### 3.5 Módulo Mantenimientos

Permite registrar las operaciones de mantenimiento realizadas a cada vehículo.

**Acceso:**
1. Selecciona un vehículo en la pantalla principal
2. Pulsa **Ver Mantenimientos**

**Ventana de mantenimientos:**

La ventana se organiza en dos paneles:
- **Panel superior**: lista de mantenimientos del vehículo
- **Panel inferior**: piezas utilizadas en el mantenimiento seleccionado

Al seleccionar un mantenimiento en el panel superior, las piezas asociadas se muestran automáticamente en el panel inferior.

**Crear un mantenimiento:**

1. Pulsa **Nuevo Mantenimiento**
2. Selecciona la **Fecha**
3. Introduce los **KM** actuales
4. Introduce el **Coste Total** (opcional)
5. Añade **Observaciones** (opcional)
6. En la sección **Piezas**:
   - Selecciona una pieza del desplegable
   - Introduce la **Cantidad**
   - El **Precio Unitario** se autocompleta (modificable)
   - Pulsa **Agregar**
   - Repite para cada pieza utilizada
7. Pulsa **Guardar**

**Eliminar un mantenimiento:**

1. Selecciona el mantenimiento en la tabla superior
2. Pulsa **Eliminar Mantenimiento**
3. Confirma la eliminación

---

## 4. Guía de operaciones CRUD

### 4.1 Crear (CREATE)

| Módulo | Acción |
|---|---|
| Vehículos | Nuevo Vehículo → formulario → Guardar |
| Modelos | Gestión Modelos → Nuevo → formulario → Guardar |
| Piezas | Gestión Piezas → Nuevo → formulario → Guardar |
| Mantenimientos | Ver Mantenimientos → Nuevo Mantenimiento → formulario → Guardar |

### 4.2 Listar (READ)

Todos los módulos cargan automáticamente los datos al abrirse. Usa el botón **Refrescar** en la pantalla principal para actualizar los datos manualmente.

### 4.3 Editar (UPDATE)

| Módulo | Acción |
|---|---|
| Vehículos | Seleccionar vehículo → Editar Vehículo → modificar → Guardar |
| Modelos | Gestión Modelos → seleccionar → Editar → modificar → Guardar |
| Piezas | Gestión Piezas → seleccionar → Editar → modificar → Guardar |

### 4.4 Eliminar (DELETE)

| Módulo | Acción | Condición |
|---|---|---|
| Vehículos | Seleccionar → Eliminar Vehículo → confirmar | Elimina mantenimientos asociados |
| Modelos | Gestión Modelos → seleccionar → Eliminar → confirmar | No debe tener vehículos asociados |
| Piezas | Gestión Piezas → seleccionar → Eliminar → confirmar | — |
| Mantenimientos | Ver Mantenimientos → seleccionar → Eliminar → confirmar | — |

---

## 5. Consejos de uso y buenas prácticas

### 5.1 Antes de crear un vehículo

Asegúrate de que el modelo del vehículo ya existe en el catálogo. Puedes crearlo desde **Gestión Modelos** antes de registrar el vehículo.

### 5.2 Catálogo actualizado

Mantén el catálogo de modelos y piezas actualizado. Un catálogo bien organizado facilita y agiliza el registro de vehículos y mantenimientos.

### 5.3 Precios en mantenimientos

Aunque el precio unitario se autocompleta al seleccionar una pieza, puedes ajustarlo manualmente si el proveedor aplicó un precio distinto al del catálogo.

### 5.4 Trabajo multiusuario

Si varios usuarios utilizan la aplicación simultáneamente, usa el botón **Refrescar** periódicamente para ver los datos más recientes.

### 5.5 Planificación de mantenimientos

Revisa regularmente el kilometraje de los vehículos para planificar los mantenimientos según las recomendaciones del fabricante.

---

## 6. Problemas frecuentes y soluciones

### 6.1 Error de conexión con el servidor

**Síntoma:** Mensaje de error al iniciar o al realizar operaciones.

**Soluciones:**
1. Verifica que el servidor backend esté ejecutándose
2. Confirma que la URL sea `http://localhost:8080`
3. Comprueba que no haya un cortafuegos bloqueando el puerto 8080
4. Reinicia el servidor backend

### 6.2 Error al guardar un modelo

**Síntoma:** Error del servidor al crear o editar un modelo.

**Soluciones:**
- Verifica que los campos Marca y Modelo no estén vacíos
- Comprueba que el año sea un número válido
- Asegúrate de que el servidor backend funcione correctamente

### 6.3 Error al guardar un mantenimiento

**Síntoma:** El mantenimiento no se guarda.

**Soluciones:**
- Selecciona una fecha válida
- Introduce los KM como número
- Verifica que el vehículo asociado exista en la base de datos

### 6.4 Tabla vacía

**Síntoma:** Una tabla aparece vacía cuando debería mostrar datos.

**Soluciones:**
- Pulsa **Refrescar** para recargar
- Verifica que el servidor esté ejecutándose
- Comprueba que la base de datos contenga registros

### 6.5 No se puede eliminar un modelo

**Síntoma:** Error al intentar eliminar un modelo.

**Solución:** El modelo tiene vehículos asociados. Elimina primero los vehículos que usan ese modelo o asígnales otro modelo diferente.

### 6.6 La aplicación no inicia

**Síntoma:** La aplicación no se abre o muestra un error de Java.

**Soluciones:**
- Verifica que Java 17+ esté instalado: `java -version`
- Comprueba que el archivo JAR no esté dañado
- Ejecuta desde terminal para ver errores detallados

### 6.7 Error de compilación con Maven

**Síntoma:** Error al ejecutar `mvnw.cmd compile`.

**Soluciones:**
- Asegúrate de tener JDK 17 configurado como `JAVA_HOME`
- Verifica la conexión a internet (descarga de dependencias)
- Limpia el caché de Maven: `mvnw.cmd clean`

---

## 7. Estructura del proyecto

```
garagelog-desktop/
├── docs/
│   └── manual-de-usuario.md
├── src/
│   ├── main/java/org/garagelog/desktop/
│   │   ├── Main.java                        # Punto de entrada
│   │   ├── api/
│   │   │   └── ApiClient.java               # Cliente HTTP para API REST
│   │   ├── controller/
│   │   │   ├── MainController.java          # Controlador principal
│   │   │   ├── VehiculoFormController.java  # Formulario vehículo
│   │   │   ├── ModeloController.java        # Listado modelos
│   │   │   ├── ModeloFormController.java    # Formulario modelo
│   │   │   ├── PiezaController.java         # Listado piezas
│   │   │   ├── PiezaFormController.java     # Formulario pieza
│   │   │   ├── MantenimientoController.java # Listado mantenimientos
│   │   │   ├── MantenimientoFormController.java # Formulario mantenimiento
│   │   │   └── HelpController.java          # Sistema de ayuda
│   │   └── model/
│   │       ├── ModeloVehiculo.java
│   │       ├── Vehiculo.java
│   │       ├── Pieza.java
│   │       ├── Mantenimiento.java
│   │       ├── MantenimientoPieza.java
│   │       └── MantenimientoRequest.java
│   └── main/resources/org/garagelog/desktop/
│       ├── main-view.fxml
│       ├── vehiculo-form-view.fxml
│       ├── modelos-view.fxml
│       ├── modelo-form-view.fxml
│       ├── piezas-view.fxml
│       ├── pieza-form-view.fxml
│       ├── mantenimientos-view.fxml
│       ├── mantenimiento-form-view.fxml
│       ├── help-view.fxml
│       └── help-content.html
├── pom.xml
└── manual-de-usuario.md (este archivo)
```

---

## 8. Soporte técnico

Para reportar incidencias o sugerir mejoras, contacta con el equipo de desarrollo a través del repositorio oficial del proyecto.

---

*Documento generado el 24 de mayo de 2026*
*Versión de la aplicación: 1.0-SNAPSHOT*
