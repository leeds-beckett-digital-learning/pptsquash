@echo off

SET NAME="PPt Squasher"
SET VERSION=1.0
SET DESTA=build\installer\selfinstall\
SET DESTB=build\installer\admininstall\
PATH %USERPROFILE%\Software\wix;%PATH%

echo Tip: rebuild gradle project before running this.
pause

IF NOT EXIST %DESTA% MKDIR %DESTA%
IF NOT EXIST %DESTB% MKDIR %DESTB%

del /Q %DESTA%*.exe
del /Q %DESTB%*.exe

echo Packaging self install...
jpackage -n %NAME% --app-version %VERSION% --dest %DESTA% --main-jar build\libs\pptsquash.jar -i . --icon icon.ico --win-per-user-install --win-menu
ren %DESTA%%NAME%-%VERSION%.exe %NAME%-%VERSION%" Self Install".exe

echo Packaging admin install...
jpackage -n %NAME% --app-version %VERSION% --dest %DESTB% --main-jar build\libs\pptsquash.jar -i . --icon icon.ico --win-menu
ren %DESTB%%NAME%-%VERSION%.exe %NAME%-%VERSION%" Admin Install".exe

echo Finished
