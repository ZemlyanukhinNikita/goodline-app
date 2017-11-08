#!/bin/bash
source ./CONFIG.sh
if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	CP="$OUTLIB;$OUTJAR"
elif [ "$(expr substr $(uname -s) 1 10)" == "Linux" ]; then
	CP="$OUTLIB:$OUTJAR"
fi

java -cp "$CP" "$MAIN" $@
