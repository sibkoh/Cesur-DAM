@echo off
echo ====================================================
echo   Iniciando Reserva de Eventos (Modo Hibrido)
echo ====================================================
echo.

REM --- CONFIGURACION ---
REM 1. Definimos donde estan las librerias (La carpeta que creo Eclipse)
set PATH_TO_FX="Reserva_lib"

REM 2. Definimos tu clase principal (La que creamos para engañar a Java)
REM    Asegurate de que este nombre es correcto segun tu paquete:
set MAIN_CLASS=com.reserva.app.AppLauncher

REM --- EJECUCION ---
echo Lanzando aplicacion con JavaFX en el Module-Path...

java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.graphics --enable-native-access=ALL-UNNAMED -cp "ReservaFinal.jar;%PATH_TO_FX%/*" %MAIN_CLASS%

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Algo fallo.
    pause
)