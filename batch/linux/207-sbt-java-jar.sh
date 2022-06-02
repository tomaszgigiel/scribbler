#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; sbt clean assembly; java -jar target/scala-3.1.1/scribbler-sbt-uberjar.jar pl.tomaszgigiel.scribbler.ScalaApp src/test/resources/custom.edn;cd -) #
