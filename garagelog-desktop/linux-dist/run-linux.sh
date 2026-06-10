#!/bin/bash
# GarageLog Desktop - Ejecutar en Linux
# Coloca este script junto al JAR y la carpeta lib/

JAR="garagelog-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar"
LIBDIR="lib"
JFX_VER="21"

# Verificar que existe el JAR
if [ ! -f "$JAR" ]; then
    echo "ERROR: No se encuentra $JAR"
    echo "Coloca este script en la misma carpeta que el JAR."
    exit 1
fi

# Descargar JavaFX Linux JARs si no existen
if [ ! -d "$LIBDIR" ]; then
    mkdir -p "$LIBDIR"
fi

for mod in base graphics controls fxml web media; do
    if [ ! -f "$LIBDIR/javafx-${mod}-${JFX_VER}-linux.jar" ]; then
        echo "Descargando javafx-${mod}-${JFX_VER}-linux.jar..."
        wget -q "https://repo1.maven.org/maven2/org/openjfx/javafx-${mod}/${JFX_VER}/javafx-${mod}-${JFX_VER}-linux.jar" \
            -O "$LIBDIR/javafx-${mod}-${JFX_VER}-linux.jar"
    fi
done

echo "Iniciando GarageLog Desktop..."
java \
    --module-path "$LIBDIR" \
    --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.media \
    --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
    --add-exports javafx.web/com.sun.javafx.sg.prism.web=ALL-UNNAMED \
    -jar "$JAR"
