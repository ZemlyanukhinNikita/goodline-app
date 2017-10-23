@echo off
javac -sourcepath ./src -classpath lib/commons-cli-1.4.jar src/Main.java
echo "HELP"
java -classpath "src/;lib/commons-cli-1.4.jar"; Main
echo "HELP"
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -h
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty
echo "0" %ERRORLEVEL% 
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Oleg -p qwerty
echo "1" %ERRORLEVEL% 
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p 123
echo "2" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B.C.D
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r ROAD -pt A.B
echo "3" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya123 -p 123 -r EXECUTE -pt A.B
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt G.G
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.BC
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.GGG
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya123 -p 123 -r READ -pt H.I.J
echo "3" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya123 -p 123 -r EXECUTE -pt H.I.J
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya123 -p 123 -r EXECUTE -pt H.I.J.K
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya123 -p 123 -r EXECUTE -pt H.I
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v 100
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2017-10-08 -v 100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v str100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v 100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v str100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; Main -l Vasya -p qwerty -r READ -pt H.I.J
echo "0" %ERRORLEVEL%
PAUSE