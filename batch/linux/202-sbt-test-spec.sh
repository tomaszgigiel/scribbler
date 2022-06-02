#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; sbt clean test "Test/testOnly pl.tomaszgigiel.scribbler.*Suite"; cd -) #
