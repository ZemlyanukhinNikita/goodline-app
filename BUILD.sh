#!/bin/bash

rm -rf "out/classes"

if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	mkdir "out/classes"
	CLASSES="out/classes/"
	SRC="src"
	CP="lib/*"
	MAIN1="main.Main"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	mkdir "out/classes"
	CLASSES="out/classes/"
	SRC="src"
	CP="lib/*"
	MAIN1="main.Main"
fi
find . -name "*.java" | xargs javac -cp "$CP" -d $CLASSES -sourcepath $SRC

mkdir -p "out/lib/"
cp $CP "out/lib/"
cd out
cd classes
JAR="../Main.jar"
CLASSES_JAR="./"
jar -cfe "$JAR" "$MAIN1" $CLASSES_JAR
