#!/bin/bash

source ./CONFIG.sh

OUT="out/classes"
rm -rf "$OUT"
mkdir -p $OUT

if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	CP="$LIB"
elif [ "$(expr substr $(uname -s) 1 10)" == "Linux" ]; then
	CP="$LIB"
fi

find . -name "*.java" | xargs javac -cp "$CP" -d $OUT -sourcepath $SRC

mkdir -p "out/lib/"
cp $CP "out/lib/"
cd out
cd classes
JAR="../Main.jar"
CLASSES_JAR="./"
jar -cfe "$JAR" "$MAIN" $CLASSES_JAR
