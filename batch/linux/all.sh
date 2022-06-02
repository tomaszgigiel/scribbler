#!/bin/bash
DIR_PROJECT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"/../.. #
#
(cd $DIR_PROJECT; ./batch/linux/001-bash-info.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/002-bash-rm-target.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/003-bash-sed-replace-end-of-line.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/004-bash-grep-todo.sh; cd -) #
###(cd $DIR_PROJECT; ./batch/linux/005-bash-sed-replace-string.sh; cd -) #
###(cd $DIR_PROJECT; ./batch/linux/006-sed-smooth-log-after.sh; cd -) #
#
(cd $DIR_PROJECT; ./batch/linux/101-mvn-compile.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/102-mvn-test-spec.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/103-mvn-test.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/104-mvn-exec.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/105-mvn-exec-specific.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/106-mvn-java-cp.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/107-mvn-java-jar.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/108-mvn-uberjar.sh; cd -) #
#(cd $DIR_PROJECT; ./batch/linux/109-mvn-jshell.sh; cd -) #
#
(cd $DIR_PROJECT; ./batch/linux/201-sbt-compile.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/202-sbt-test-spec.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/203-sbt-test.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/204-sbt-run.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/205-sbt-run-specific.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/206-sbt-java-cp.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/207-sbt-java-jar.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/208-sbt-uberjar.sh; cd -) #
#(cd $DIR_PROJECT; ./batch/linux/208-sbt-console.sh; cd -) #
#
(cd $DIR_PROJECT; ./batch/linux/301-lein-compile.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/302-lein-test-spec.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/303-lein-test.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/304-lein-run.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/305-lein-run-specific.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/306-lein-java-cp.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/307-lein-java-jar.sh; cd -) #
(cd $DIR_PROJECT; ./batch/linux/308-lein-uberjar.sh; cd -) #
#(cd $DIR_PROJECT; ./batch/linux/309-lein-repl.sh; cd -) #
