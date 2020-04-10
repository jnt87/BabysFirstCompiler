echo off
mkdir build
dir /A-D /B /S *.java > sources.txt
javac.exe -d build @sources.txt
java -cp ./build Compiler example/example.ir out.ir
echo on