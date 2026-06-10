@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
set JFX_VERSION=21
set M2=%USERPROFILE%\.m2\repository\org\openjfx
set JFX_MODULES=%M2%\javafx-base\%JFX_VERSION%\javafx-base-%JFX_VERSION%-win.jar;%M2%\javafx-graphics\%JFX_VERSION%\javafx-graphics-%JFX_VERSION%-win.jar;%M2%\javafx-controls\%JFX_VERSION%\javafx-controls-%JFX_VERSION%-win.jar;%M2%\javafx-fxml\%JFX_VERSION%\javafx-fxml-%JFX_VERSION%-win.jar
set JAR=target\garagelog-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar
cd /d "%~dp0"
if not exist "%JAR%" (
    echo Construyendo JAR...
    call .\mvnw.cmd package -q
)
java --module-path "%JFX_MODULES%" --add-modules javafx.controls,javafx.fxml -jar "%JAR%"
pause