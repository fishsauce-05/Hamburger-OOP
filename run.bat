@echo off
title Burger Store System - Launcher

:: --- CẤU HÌNH ---
set MAIN_CLASS=com.burgerstore.ui.BurgerAppGUI
set SRC_DIR=src
set OUT_DIR=bin

echo ============================================
echo        BURGER STORE SYSTEM LAUNCHER
echo ============================================

:: 1. Tạo thư mục bin để chứa file .class nếu chưa có
if not exist %OUT_DIR% (
    echo [INFO] Creating '%OUT_DIR%' directory...
    mkdir %OUT_DIR%
)

:: 2. Biên dịch (Compile)
:: Lệnh này sẽ tìm file Main và tự động biên dịch các file phụ thuộc
echo [INFO] Compiling source code...
javac -d %OUT_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\com\burgerstore\ui\BurgerAppGUI.java

:: Kiểm tra nếu biên dịch lỗi thì dừng lại
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed! Please check your code in the src folder.
    pause
    exit /b
)

:: 3. Chạy chương trình (Run)
echo [INFO] Compilation successful!
echo [INFO] Launching Application...
echo --------------------------------------------

java -cp %OUT_DIR% %MAIN_CLASS%

echo.
echo --------------------------------------------
echo [INFO] Application closed.
pause