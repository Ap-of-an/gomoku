@rem
@rem MIT License
@rem
@rem Copyright (c) [2022] [Dmitry]
@rem
@rem Permission is hereby granted, free of charge, to any person obtaining a copy
@rem of this software and associated documentation files (the "Software"), to deal
@rem in the Software without restriction, including without limitation the rights
@rem to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
@rem copies of the Software, and to permit persons to whom the Software is
@rem furnished to do so, subject to the following conditions:
@rem
@rem The above copyright notice and this permission notice shall be included in all
@rem copies or substantial portions of the Software.
@rem
@rem THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
@rem IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
@rem FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
@rem AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
@rem LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
@rem OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
@rem SOFTWARE.
@rem

@echo off
@rem -------------------------------------------------------------------------------------------------------------------
setlocal
@rem -------------------------------------------------------------------------------------------------------------------
@rem Try to use the `java.exe` from `%JAVA_HOME%` if this environment variable set correctly:
if "%JAVA_HOME%" neq "" (
    if exist %JAVA_HOME%\bin\java.exe (
        set JAVA_CMD=%JAVA_HOME%\bin\java
    )
)
@rem -------------------------------------------------------------------------------------------------------------------
@rem Try to use the `java.exe` using `PATH` environment variable:
where /Q java
if %ERRORLEVEL% == 0 (
    set JAVA_CMD=java
)
@rem -------------------------------------------------------------------------------------------------------------------
@rem Try to use `java.exe` from JRE if `jre\bin\java.exe` exists:
if exist jre\bin\java.exe (
    set JAVA_CMD=jre\bin\java
)
@rem -------------------------------------------------------------------------------------------------------------------
if not defined JAVA_CMD (
    @rem Throw error if `java.exe` is not configured:
    echo -------------------------------------------------------------------------- >&2
    echo `java.exe` not defined! Install or configure JVM before using this script! >&2
    echo -------------------------------------------------------------------------- >&2
    set RETURN_CODE=1
) else (
    @rem Run tic-tac-toe game:
    %JAVA_CMD% -jar ${project.build.finalName}-release.jar
    set RETURN_CODE=0
)
@rem -------------------------------------------------------------------------------------------------------------------
exit /b %RETURN_CODE%