# Manual de Usuario — GarageLog Desktop

---

## 1. Introducción

GarageLog Desktop es una aplicación de escritorio para la gestión integral de talleres mecánicos. Permite administrar vehículos, modelos, piezas y mantenimientos de forma centralizada.

La aplicación se conecta a un servidor backend Spring Boot que almacena los datos en MySQL.

### 1.1 Requisitos del sistema

| Requisito | Especificación |
|---|---|
| Sistema operativo | Windows 10+ / Linux (Ubuntu 22.04+) |
| Java | JRE 17 o superior |
| Servidor backend | Spring Boot en `http://localhost:8080` |
| Base de datos | MySQL 8.0+ |
| RAM mínima | 512 MB (1 GB recomendado) |
| Resolución | 1024 × 768 o superior |

### 1.2 Estructura de la aplicación

La ventana principal se organiza en:

- **Menú superior** — acceso a Ayuda y Acerca de
- **Tabla de vehículos** — listado completo con ID, Matrícula, Modelo, KM Iniciales y Fecha de Compra
- **Sección Vehículos** — botones Nuevo, Editar, Eliminar, Ver Mantenimientos y Refrescar
- **Sección Gestión** — botones Gestión Modelos y Gestión Piezas

Los botones Editar, Eliminar y Ver Mantenimientos están deshabilitados hasta que se seleccione un vehículo en la tabla.

---

## 2. Instalación y ejecución

### 2.1 En Windows

1. Asegúrate de tener Java 17+ instalado: `java -version`
2. Ejecuta el instalador `GarageLog-1.0.exe` desde la carpeta `dist/`
3. Inicia el servidor backend (Spring Boot)
4. Abre GarageLog Desktop desde el acceso directo

### 2.2 En Linux (Ubuntu)

1. Copia la carpeta `linux-dist/` a la máquina Ubuntu
2. Asegúrate de tener Java 17+ instalado: `java -version`
3. Inicia el servidor backend
4. Ejecuta:

```bash
cd linux-dist
chmod +x run-linux.sh
./run-linux.sh
```

### 2.3 En desarrollo (cualquier SO)

```bash
# Iniciar backend (en la carpeta backend/)
./mvnw.cmd spring-boot:run

# Iniciar aplicación desktop
./mvnw.cmd javafx:run
```

---

## 3. Pantalla principal

Al iniciar la aplicación se muestra la ventana principal con el listado de vehículos.

### 3.1 Elementos de la interfaz

| Elemento | Descripción |
|---|---|
| Menú **Ayuda** | Acceso a la ayuda interactiva y la ventana "Acerca de" |
| Tabla de vehículos | Muestra todos los vehículos registrados |
| Botón **Nuevo** | Crea un nuevo vehículo |
| Botón **Editar** | Modifica el vehículo seleccionado |
| Botón **Eliminar** | Elimina el vehículo seleccionado |
| Botón **Ver Mantenimientos** | Abre los mantenimientos del vehículo seleccionado |
| Botón **Refrescar** | Recarga los datos desde el servidor |
| Botón **Gestión Modelos** | Abre el catálogo de modelos |
| Botón **Gestión Piezas** | Abre el catálogo de piezas |

### 3.2 Comportamiento de los botones

Los botones que requieren una selección previa (Editar, Eliminar, Ver Mantenimientos) permanecen deshabilitados hasta que se selecciona un vehículo en la tabla. El botón Refrescar siempre está disponible.

---

## 4. Módulo Vehículos

### 4.1 Crear un vehículo

1. Pulsa **Nuevo** en la sección Vehículos
2. Selecciona un **Modelo** del desplegable (debe existir previamente en el catálogo)
3. Introduce la **Matrícula**
4. Introduce los **KM Iniciales** (solo números)
5. Selecciona la **Fecha de Compra**
6. Añade **Notas** (opcional)
7. Pulsa **Guardar**

### 4.2 Editar un vehículo

1. Selecciona el vehículo en la tabla principal
2. Pulsa **Editar**
3. Modifica los campos necesarios
4. Pulsa **Guardar**

> Puedes cambiar el modelo del vehículo seleccionando otro del desplegable.

### 4.3 Eliminar un vehículo

1. Selecciona el vehículo en la tabla
2. Pulsa **Eliminar**
3. Confirma la operación en el diálogo

> ⚠️ Al eliminar un vehículo se eliminan también **todos sus mantenimientos asociados**.

---

## 5. Módulo Modelos

Gestiona el catálogo de modelos de vehículos (marca, modelo, año, combustible).

**Acceso:** Pulsa **Gestión Modelos** en la sección Gestión de la pantalla principal.

### 5.1 Campos del formulario

| Campo | Obligatorio | Descripción |
|---|---|---|
| Marca | Sí | Fabricante (Toyota, BMW, etc.) |
| Modelo | Sí | Nombre del modelo (Corolla, X5, etc.) |
| Año | No | Año de fabricación |
| Combustible | No | Gasolina, Diésel, Híbrido, Eléctrico, GLP |

### 5.2 Operaciones

- **Crear:** pulsa **Nuevo**, rellena el formulario, **Guardar**
- **Editar:** selecciona un modelo, pulsa **Editar**, modifica, **Guardar**
- **Eliminar:** selecciona un modelo, pulsa **Eliminar**, confirma

> ⚠️ No se puede eliminar un modelo que tenga vehículos asociados. Elimina o reasigna los vehículos primero.

---

## 6. Módulo Piezas

Gestiona el catálogo de piezas y recambios del taller.

**Acceso:** Pulsa **Gestión Piezas** en la sección Gestión.

### 6.1 Campos del formulario

| Campo | Obligatorio | Descripción |
|---|---|---|
| Nombre | Sí | Nombre de la pieza (Filtro aceite, Pastillas freno, etc.) |
| Descripción | No | Información adicional |
| Precio | Sí | Precio unitario |

### 6.2 Operaciones

- **Crear:** **Nuevo** → formulario → **Guardar**
- **Editar:** seleccionar → **Editar** → modificar → **Guardar**
- **Eliminar:** seleccionar → **Eliminar** → confirmar

---

## 7. Módulo Mantenimientos

Registra las operaciones de mantenimiento realizadas a cada vehículo, incluyendo las piezas utilizadas, mano de obra y coste total.

**Acceso:**
1. Selecciona un vehículo en la pantalla principal
2. Pulsa **Ver Mantenimientos**

### 7.1 Ventana de mantenimientos

La ventana se divide en dos paneles:

- **Superior:** lista de mantenimientos del vehículo (ID, Fecha, KM, Coste Total, Observaciones)
- **Inferior:** piezas utilizadas en el mantenimiento seleccionado

Al seleccionar un mantenimiento en el panel superior, sus piezas asociadas se muestran automáticamente en el panel inferior.

### 7.2 Crear un mantenimiento

1. Pulsa **Nuevo Mantenimiento**
2. Selecciona la **Fecha**
3. Introduce los **KM** actuales
4. Añade **Observaciones** (opcional)
5. En la sección **Piezas**:
   - Selecciona una pieza del desplegable
   - Introduce la **Cantidad**
   - El **Precio Unitario** se autocompleta desde el catálogo (modificable)
   - Pulsa **Agregar**
   - Repite para cada pieza usada
6. Introduce la **Mano de obra** (opcional, se suma al coste total)
7. El **Coste Total** se calcula automáticamente (suma de cantidad × precio unitario + mano de obra)
8. Pulsa **Guardar**

### 7.3 Eliminar un mantenimiento

1. Selecciona el mantenimiento en la tabla superior
2. Pulsa **Eliminar Mantenimiento**
3. Confirma la eliminación

---

## 8. Ayuda y Acerca de

### 8.1 Ayuda interactiva

Desde el menú **Ayuda > Ayuda** se abre una ventana con la guía de uso completa de la aplicación, organizada por secciones con navegación lateral.

### 8.2 Acerca de

Desde el menú **Ayuda > Acerca de** se muestra la información de la aplicación: nombre, versión y descripción.

---

## 9. Consejos de uso

- **Antes de crear un vehículo**, asegúrate de que el modelo exista en el catálogo. Créalo desde **Gestión Modelos** si es necesario.
- **Precios en mantenimientos:** el precio unitario se autocompleta al seleccionar una pieza, pero puedes ajustarlo si el precio real difiere del catálogo.
- **Varios usuarios:** si trabajas con otros usuarios simultáneamente, usa **Refrescar** para ver los datos más recientes.
- **Mantenimientos periódicos:** revisa el kilometraje de los vehículos para planificar los mantenimientos según las recomendaciones del fabricante.

---

## 10. Problemas frecuentes

| Problema | Causa | Solución |
|---|---|---|
| Error de conexión | Backend no iniciado | Inicia el servidor Spring Boot |
| Tabla vacía | Sin datos o backend caído | Verifica el servidor y pulsa Refrescar |
| No se puede eliminar un modelo | Tiene vehículos asociados | Elimina o reasigna los vehículos primero |
| La aplicación no inicia | Java no instalado o JAR dañado | Verifica `java -version` o reinstala |
| Error al guardar | Campo obligatorio vacío o servidor caído | Revisa el formulario y el backend |

---

## 11. Distribución para Linux

Para ejecutar la aplicación en Ubuntu:

1. Ejecuta `prepare-linux.bat` en Windows para generar la carpeta `linux-dist/`
2. Copia la carpeta `linux-dist/` completa a la máquina Ubuntu
3. En Ubuntu, abre una terminal en la carpeta y ejecuta:

```bash
chmod +x run-linux.sh
./run-linux.sh
```

El script `run-linux.sh` configura automáticamente los módulos de JavaFX necesarios.

---

## 12. Soporte

Para reportar errores o sugerir mejoras, contacta con el equipo de desarrollo.

---

*Documento actualizado: junio 2026*
*Versión: 1.0-SNAPSHOT*
