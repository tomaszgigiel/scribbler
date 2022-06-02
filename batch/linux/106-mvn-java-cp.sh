#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; mvn -U clean package; java -cp target/scribbler-mvn-jar-with-dependencies.jar pl.tomaszgigiel.scribbler.JavaApp src/test/resources/custom.edn;cd -) #
