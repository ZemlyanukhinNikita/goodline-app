#!/bin/bash
counter=0
test () {
    ./RUN.sh $1
    parametrs=$?
    if [[ $parametrs -ne $2 ]]; then
        echo FAIL $1   $2 - $parametrs
        ((counter++))
    else
        echo OK $1   $2 - $parametrs
    fi
}

./BUILD.sh login password

test "" 0
test "-h" 0

test "-l XXX -p XXX" 1
test "-l jdoe -p XXX" 2
test "-l jdoe -p sup3rpaZZ" 0

test "-l jdoe -p sup3rpaZZ -r READ -pt \"a\"" 0
test "-l jdoe -p sup3rpaZZ -r READ -pt \"a.b\"" 0
test "-l jdoe -p sup3rpaZZ -r XXX -pt \"a.b\"" 3
test "-l jdoe -p sup3rpaZZ -r READ -pt XXX" 4
test "-l jdoe -p sup3rpaZZ -r WRITE -pt \"a\"" 4
test "-l jdoe -p sup3rpaZZ -r WRITE -pt \"a.bc\"" 4

test "-l jdoe -p sup3rpaZZ -r READ -pt a -ds \"2015-05-01\" -de \"2015-05-02\" -v 100" 0
test "-l jdoe -p sup3rpaZZ -r READ -pt a -ds XXX -de XXX -v XXX" 5
test "-l jdoe -p sup3rpaZZ -r READ -pt a -ds \"2015-05-01\" -de \"2015-05-02\" -v XXX" 5

test "" 0
test "-h" 0

test "-l Vasya -p qwerty" 0
test "-l Oleg -p qwerty" 1
test "-l Vasya -p 123" 2

test "-l Vasya -p qwerty -r READ -pt A.B" 0
test "-l Vasya -p qwerty -r READ -pt A.B.C.D" 0
test "-l Vasya -p qwerty -r ROAD -pt A.B" 3
test "-l Vasya123 -p 123 -r EXECUTE -pt A.B" 4
test "-l Vasya -p qwerty -r READ -pt G.G" 4
test "-l Vasya -p qwerty -r READ -pt A.BC" 4
test "-l Vasya -p qwerty -r READ -pt A.GGG" 4
test "-l Vasya123 -p 123 -r READ -pt H.I" 4
test "-l Vasya123 -p 123 -r EXECUTE -pt H.I.J" 0
test "-l Vasya123 -p 123 -r EXECUTE -pt H.I.J.K" 0
test "-l Vasya123 -p 123 -r EXECUTE -pt H.I.J" 0

test "-l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v 100" 0
test "-l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2017-10-08 -v 100" 5
test "-l Vasya -p qwerty -r READ -pt A.B -ds 2017-10-08 -de 2017-10-08 -v str100" 5
test "-l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v 100" 5
test "-l Vasya -p qwerty -r READ -pt A.B -ds 1111111111 -de 2222222222 -v str100" 5

test "-l Vasya -p qwerty -r READ -pt H.I.J" 0
test "-l Vasya123 -p 123 -r EXECUTE -pt DDD" 0
test "-l Vasya -p qwerty -r WRITE -pt H.I.J" 0
test "-l Vasya -p qwerty -r WRITE -pt A.B" 4
test "-l Vasya -p qwerty -r WRIRE -pt A.B" 3
test "-l Vasya123 -p 123 -r READ -pt DDD" 0

#По скольку это последний тест, можем переопределить переменные окружения
LOGIN=admin1
PASSWORD=db_h2_17
test "" 255

if [[ $counter -eq 0 ]]; then
    echo ALL TESTS OK
else
    echo $counter TESTS FAIL
fi
exit $counter
