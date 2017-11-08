#!/bin/bash
source ./CONFIG.sh
if [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
	CLP="$CP"
elif [ "$(expr substr $(uname -s) 1 10)" == "Linux" ]; then
	CLP="$CP"
fi

java -cp "$CLP" "$MAIN" $@
