@echo off
set MAIN_CLASS=com.burgerstore.ui.BurgerAppGUI
set SRC_DIR=src
set OUT_DIR=bin

if not exist %OUT_DIR% (
    mkdir %OUT_DIR%
)
javac -d %OUT_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\com\burgerstore\ui\BurgerAppGUI.java

if %errorlevel% neq 0 (
    pause
    exit /b
)

java -cp %OUT_DIR% %MAIN_CLASS%

pause