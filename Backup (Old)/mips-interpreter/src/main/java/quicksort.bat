echo off
mkdir build
dir /A-D /B /S *.java > sources.txt
javac.exe -d build @sources.txt
java -cp ./build Compiler public_test_cases\quicksort\quicksort.ir out.ir
echo on