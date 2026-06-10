$JFX_VERSION = "21.0.2"
$DIST_DIR = "dist"
$JAR = "garagelog-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar"
$JDK_PATH = "C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"

if (-not $env:JAVA_HOME) {
    $env:JAVA_HOME = $JDK_PATH
    $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
}

$jpackage = if (Get-Command jpackage -ErrorAction SilentlyContinue) { "jpackage" }
    elseif (Test-Path "$env:JAVA_HOME\bin\jpackage.exe") { "$env:JAVA_HOME\bin\jpackage.exe" }
    else { Write-Host "ERROR: jpackage no encontrado" -ForegroundColor Red; exit 1 }

Write-Host "=== Compilando ===" -ForegroundColor Cyan
.\mvnw.cmd clean package -q
if ($LASTEXITCODE -ne 0) { Read-Host "Error compilando"; exit 1 }

# Descargar JMODs si no existen
$jmodsDir = Join-Path (Get-Location) "javafx-jmods-$JFX_VERSION"
if (-not (Test-Path "$jmodsDir\javafx.base.jmod")) {
    Write-Host "=== Descargando JavaFX JMODs ===" -ForegroundColor Cyan
    $hash = "0dc89a29ffa34addbe3057926acea09a"
    $url = "https://download.java.net/java/GA/javafx$JFX_VERSION/$hash/GPL/openjfx-$($JFX_VERSION)_windows-x64_bin-jmods.zip"
    $zip = "javafx-jmods.zip"
    try {
        Invoke-WebRequest -Uri $url -OutFile $zip -UseBasicParsing
        Expand-Archive -Path $zip -DestinationPath "." -Force
        Remove-Item $zip
    } catch {
        Write-Host "Error descargando: $_" -ForegroundColor Red
        Write-Host "Prueba manual: $url" -ForegroundColor Yellow
        exit 1
    }
}

$jdkJmods = "$env:JAVA_HOME\jmods"
$modulePath = "$jdkJmods;$jmodsDir"

Write-Host "=== Generando aplicación portable ===" -ForegroundColor Cyan
& $jpackage --type app-image `
    --name "GarageLog" `
    --app-version "1.0" `
    --vendor "GarageLog" `
    --input "target" `
    --main-jar $JAR `
    --main-class "org.garagelog.desktop.Main" `
    --module-path $modulePath `
    --add-modules "javafx.controls,javafx.fxml" `
    --dest $DIST_DIR

if ($LASTEXITCODE -eq 0) {
    Write-Host "=== App generada en: $DIST_DIR\GarageLog ===" -ForegroundColor Green
    Write-Host "Ejecuta: $DIST_DIR\GarageLog\GarageLog.exe" -ForegroundColor Green
} else {
    Write-Host "=== Error ===" -ForegroundColor Red
}
Read-Host "Presiona Enter"
