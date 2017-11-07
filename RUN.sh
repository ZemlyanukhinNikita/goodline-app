#!/bin/bash

if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	CP="out/lib/*;out/Main.jar"
	MAIN2="main.Main"
fi
if [ "$(expr substr $(uname -s) 1 10)" == "Linux" ]; then
	CP="out/lib/*:out/Main.jar"
	MAIN2="main.Main"
fi

java -cp "$CP" "$MAIN2" $@
