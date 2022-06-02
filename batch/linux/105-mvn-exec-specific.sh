#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; mvn -U clean compile exec:java -Dexec.mainClass="pl.tomaszgigiel.scribbler.JavaApp" -Dexec.args="my args"; cd -) #
