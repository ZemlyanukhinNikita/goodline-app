@echo off
javac Main.java
java Main Vasya qwerty
echo "0" %ERRORLEVEL% 
java Main Oleg qwerty
echo "1" %ERRORLEVEL% 
java Main Vasya 123
echo "2" %ERRORLEVEL%

java Main Vasya qwerty READ A.B
echo "0" %ERRORLEVEL%
java Main Vasya qwerty READ A.B.C.D
echo "0" %ERRORLEVEL%
java Main Vasya qwerty ROAD A.B
echo "3" %ERRORLEVEL%
java Main Vasya123 123 EXECUTE A.B
echo "4" %ERRORLEVEL%
java Main Vasya qwerty READ G.G
echo "4" %ERRORLEVEL%
java Main Vasya qwerty READ A.BC
echo "4" %ERRORLEVEL%
java Main Vasya qwerty READ A.GGG
echo "4" %ERRORLEVEL%

java Main Vasya123 123 READ H.I.J
echo "3" %ERRORLEVEL%

java Main Vasya123 123 EXECUTE H.I.J
echo "0" %ERRORLEVEL%

java Main Vasya123 123 EXECUTE H.I.J.K
echo "0" %ERRORLEVEL%
java Main Vasya123 123 EXECUTE H.I
echo "4" %ERRORLEVEL%
PAUSE