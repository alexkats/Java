#!/bin/bash
javac *.java
java -ea ParserTest
java -ea ParserTestHard
rm *.class
