@echo off

set file_name=%1

DEL \S\Q build

mkdir build
dir /A-D /B /S *.java > sources.txt
javac.exe -d build @sources.txt
java -cp ./build Compiler test_cases/%file_name%/%file_name%.ir out.ir

move out.s mips-interpreter\
@echo on