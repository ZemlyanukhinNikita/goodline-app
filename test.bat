@echo off
javac -sourcepath ./src -classpath lib/commons-cli-1.4.jar src/main/Main.java
echo "HELP"
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main
echo "HELP"
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -h
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty
echo "0" %ERRORLEVEL% 
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Oleg -p qwerty
echo "1" %ERRORLEVEL% 
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p 123
echo "2" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B.C.D
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r ROAD -pt A.B
echo "3" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r EXECUTE -pt A.B
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt G.G
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.BC
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.GGG
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r READ -pt H.I
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r EXECUTE -pt H.I.J
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r EXECUTE -pt H.I.J.K
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r EXECUTE -pt H.I.J
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v 100
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2017-10-08 -v 100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v str100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v 100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v str100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r READ -pt H.I.J
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r EXECUTE -pt DDD
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r WRITE -pt H.I.J
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r WRITE -pt A.B
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya -p qwerty -r WRIRE -pt A.B
echo "3" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l Vasya123 -p 123 -r READ -pt DDD
echo "0" %ERRORLEVEL%

java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -h
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l XXX -p XXX
echo "1" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l jdoe -p XXX
echo "2" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l jdoe -p sup3rpaZZ
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l xxx -p yyy
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main -l jdoe -p sup3rpaZZ
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt a
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt a.b
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r XXX -pt a.b
echo "3" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt XXX
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r WRITE -pt a
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r WRITE -pt a.bc
echo "4" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt a.b -ds 2015-01-01 -de 2015-12-31 -v 100
echo "0" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt a.b -ds 01-01-2015 -de 2015-12-31 -v 100
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l jdoe -p sup3rpaZZ -r READ -pt a.b -ds 2015-01-01 -de 2015-12-31 -v XXX
echo "5" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l X -p X -r READ -pt X -ds 2015-01-01 -de 2015-12-31 -v XXX
echo "1" %ERRORLEVEL%
java -classpath "src/;lib/commons-cli-1.4.jar"; main.Main  -l X -p X -r READ -pt X
echo "1" %ERRORLEVEL%
PAUSE