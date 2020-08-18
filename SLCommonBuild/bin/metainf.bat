@echo off

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.\

%DIRNAME%\groovy.bat %DIRNAME%\metainf.groovy %*
