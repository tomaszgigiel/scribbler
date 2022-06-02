#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; java --version; scala -version; clj -version; mvn -version; sbt -version; lein -version; cd -) #
