#!/bin/bash

OUT="out/classes"

if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	SRC="src"
	CP="lib/*"
	MAIN1="main.Main"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
	SRC="src"
	CP="lib/*"
	MAIN1="main.Main"
fi

mkdir -p $OUT
rm -rf "$OUT"

find . -name "*.java" | xargs javac -cp "$CP" -d $OUT -sourcepath $SRC

mkdir -p "out/lib/"
cp $CP "out/lib/"
cd out
cd classes
JAR="../Main.jar"
CLASSES_JAR="./"
jar -cfe "$JAR" "$MAIN1" $CLASSES_JAR
