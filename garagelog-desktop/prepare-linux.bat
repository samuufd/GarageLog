@echo off
setlocal

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot
set M2=%USERPROFILE%\.m2\repository\org\openjfx
set JFX_VER=21
set DIST=linux-dist

echo === GarageLog Desktop - Preparar distribucion para Linux ===

:: 1. Compilar
echo.
echo [1/3] Compilando...
call .\mvnw.cmd package -q
if %ERRORLEVEL% neq 0 (
    echo ERROR al compilar
    exit /b 1
)

:: 2. Crear carpeta de distribucion
echo [2/3] Creando %DIST%/...
if exist %DIST% rmdir /s /q %DIST%
mkdir %DIST%\lib

:: 3. Copiar fat JAR
copy target\garagelog-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar %DIST%\ > nul

:: 4. Copiar JavaFX Linux JARs
for %%m in (base graphics controls fxml web media) do (
    copy "%M2%\javafx-%%m\%JFX_VER%\javafx-%%m-%JFX_VER%-linux.jar" %DIST%\lib\ > nul
    echo   Copiado: javafx-%%m-%JFX_VER%-linux.jar
)

:: 5. Copiar script de ejecucion
copy run-linux.sh %DIST%\ > nul 2>nul

echo.
echo === Listo! ===
echo.
echo Contenido de %DIST%/:
dir %DIST%
echo.
echo Copia la carpeta %DIST%/ a Ubuntu y ejecuta:
echo   cd linux-dist
echo   chmod +x run-linux.sh
echo   ./run-linux.sh
echo.
echo O manualmente:
echo   java --module-path lib --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.media ^
echo        --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED ^
echo        --add-exports javafx.web/com.sun.javafx.sg.prism.web=ALL-UNNAMED ^
echo        -jar garagelog-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar

endlocal
