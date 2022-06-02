#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
# /exit
# https://stackoverflow.com/questions/47705036/in-jshell-how-to-import-classpath-from-a-maven-project
(cd $DIR_PROJECT; mvn -U clean package dependency:build-classpath -DincludeTypes=jar -Dmdep.outputFile=remove-immediately-cp.txt; jshell --class-path `cat remove-immediately-cp.txt`:target/classes; rm remove-immediately-cp.txt;cd -) #
