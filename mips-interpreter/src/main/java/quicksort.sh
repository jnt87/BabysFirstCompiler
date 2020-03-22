#!/bin/bash
mkdir build
find . -name "*.java" > sources.txt
javac -d build @sources.txt
java -cp ./build Compiler public_test_cases/quicksort/quicksort.ir out.ir
