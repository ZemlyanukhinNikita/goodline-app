@echo off
javac Main.java
java Main Vasya qwerty
echo "0" %ERRORLEVEL% 
java Main Oleg qwerty
echo "1" %ERRORLEVEL% 
java Main Vasya 123
echo "2" %ERRORLEVEL% 
PAUSE