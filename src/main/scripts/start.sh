#!/usr/bin/env sh
# MIT License
#
# Copyright (c) [2022] [Dmitry]
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#

# Try to use the `java` from `$JAVA_HOME` if this environment variable set correctly:
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ] ; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi
# ----------------------------------------------------------------------------------------------------------------------
# Try to use the `java` using `PATH` environment variable:
WHICH_JAVA=$(which java)
if [ -x "$WHICH_JAVA" ]; then
  JAVA_CMD="java"
  unset WHICH_JAVA
fi
# ----------------------------------------------------------------------------------------------------------------------
# Try to use `java` from JRE if `jre/bin/java` exists and executable:
if [ -x "jre/bin/java" ]; then
  JAVA_CMD="jre/bin/java"
fi
# ----------------------------------------------------------------------------------------------------------------------
if [ -z ${JAVA_CMD+x} ]; then
  # Throw error if `java` is not configured:
  echo "------------------------------------------------------------------------" >&2
  echo "\`java\` not defined! Install or configure JVM before using this script!" >&2
  echo "------------------------------------------------------------------------" >&2
  RETURN_CODE=1
else
  # Fix current dir issue. Read more: http://hints.macworld.com/article.php?story=20041217111834902
  cd "$(dirname "$0")" || exit
  # Run tic-tac-toe game:
  $JAVA_CMD -jar ${project.build.finalName}-release.jar
  RETURN_CODE=0
fi
# ----------------------------------------------------------------------------------------------------------------------
exit $RETURN_CODE