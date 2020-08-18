@echo off

setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.\

"%DIRNAME%\..\ant\opt\groovy\startGroovy.bat" "%DIRNAME%" groovy.ui.GroovyMain %*

@rem End local scope for the variables with windows NT shell
endlocal
