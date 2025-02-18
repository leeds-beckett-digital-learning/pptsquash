@echo off

PATH %USERPROFILE%\Software\wix;%PATH%

echo Packaging...
jpackage -n "PPtSquasher" --main-jar build\libs\pptsquash.jar -i . --icon icon.ico --win-per-user-install --win-menu
echo Finished
