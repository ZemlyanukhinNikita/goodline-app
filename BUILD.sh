#!/bin/bash
source ./CONFIG.sh

OUT="out/classes"
rm -rf "$OUT"
mkdir -p $OUT

if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	CLP="$LIB"
elif [ "$(expr substr $(uname -s) 1 10)" == "Linux" ]; then
	CLP="$LIB"
fi

find . -name "*.java" | xargs javac -cp "$CLP" -d $OUT -sourcepath $SRC

mkdir -p "out/lib/"
cp $CLP "out/lib/"
cd out
cd classes
JAR="../Main.jar"
CLASSES_JAR="./"
jar -cfe "$JAR" "$MAIN" $CLASSES_JAR
