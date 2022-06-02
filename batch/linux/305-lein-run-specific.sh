#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
(cd $DIR_PROJECT; lein do clean, run src/test/resources/custom.edn; cd -) #
#TODO: https://stackoverflow.com/questions/11023762/leiningen-with-multiple-main-classes #
